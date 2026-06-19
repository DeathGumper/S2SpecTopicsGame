import { gameController } from '../controllers/GameController'
import CreatureDisplay from './CreatureDisplay'
import { websocketConnection } from '../controllers/WebSocketConnection'

import HealthBar from './HealthBar'

import '../styles/BattleStageStyles.css'
import { useEffect, useState } from 'react'
import StatusEffectPopup from './StatusEffectPopup'

function BattleStage({ lobbyState, playerId }) {
    // Get the player
    const player = lobbyState?.players.find(p => p.id === playerId)
    // Get the opponent
    const opponent = lobbyState?.players.find(p => p.id === player.opponentId)

    // Get the creatures
    const playerCreature = player?.creatures[player.activeCreatureIndex]
    const opponentCreature = opponent?.creatures[opponent.activeCreatureIndex]

    // Status Effect Popups
    const [currPopup, setCurrPopup] = useState(null)

    // Popup handler
    useEffect(() => {
        const handler = () => {
            const updates = websocketConnection.getBattleUpdates();

            // When the creature gets burned or stunned it goes and displays a popup
            updates.forEach(({ type, payload }) => {
                if (type === 'CREATURE_BURNED') {
                    setCurrPopup(<StatusEffectPopup statusName={"BURNED"} message={"Creature " + payload.creature.name + " has been burned for " + payload.damage + "!"}/>)
                    
                    // Get rid of the popup
                    setTimeout(() => {
                        setCurrPopup(null)
                    }, 5000)
                } else if (type === 'CREATURE_STUNNED') {
                    setCurrPopup(<StatusEffectPopup statusName={payload.statusName} message={"Creature " + payload.creature.name + " has been stunned and lost their turn."}/>)

                    // Get rid of the popup
                    setTimeout(() => {
                        setCurrPopup(null)
                    }, 5000)
                }
            });
        };

        // Reset the handler
        websocketConnection.addListener(handler);
        return () => websocketConnection.removeListener(handler);
    }, []);
    

    // Visual stuff
    return (
        <div className="battle-stage">
            {/* Display the opponents name */}
            <h2 className="opponent-name">Opponent: {opponent?.name}</h2>
            {/* If there is a popup, display it */}
            {currPopup != null && currPopup}

            {/* Display the healthbars */}
            <HealthBar creatureData={opponentCreature} x={5} y={5} w={40} h={5} />
            <HealthBar creatureData={playerCreature} x={55} y={5} w={40} h={5} />

            {/* Display how many creatures the opponent has left */}
            <h3 id="opponents-creatures-left">Creatures Left: {opponent?.creatures.length}</h3>

            {/* Players creature, scaled up CreatureDisplay class */}
            <div style={{ transform: 'scale(1.5)'}} id="player-creature" className="creature-display">
                <h2>Your Creature</h2>
                <CreatureDisplay creatureData={playerCreature} active={true} turn={player.battleState == 'MY_TURN'}/>
            </div>

            {/* Opponents Creature, creatureDisplay class */}
            <div id="opponent-creature" className="creature-display">
                <h2>Opponent Creature</h2>
                <CreatureDisplay creatureData={opponentCreature} active={false} turn={opponent.battleState == 'MY_TURN'} />
            </div>

            {/* The ability button menu, shows all abillities in a row if its the players turn */}
            <div id="abilities-display">
                <h2>Abilities</h2>
                <div id="abilities-buttons">
                    {playerCreature && player.battleState == 'MY_TURN' ? (
                        Object.entries(playerCreature.abilities).map(([key, value], index) => (
                            <button key={index} className="ability-button glow-text-hover" onClick={() => gameController.useAbility(value)}>{key}</button>
                        ))
                    ) : (
                        <p>Waiting for opponent...</p>
                    )}
                </div>
                
            </div>
        </div>
    )
}

export default BattleStage
