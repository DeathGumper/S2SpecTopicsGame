import { gameController } from '../controllers/GameController'
import CreatureDisplay from './CreatureDisplay'

import HealthBar from './HealthBar'

import '../styles/BattleStageStyles.css'

function BattleStage({ lobbyState, playerId }) {
    const player = lobbyState?.players.find(p => p.id === playerId)
    const opponent = lobbyState?.players.find(p => p.id === player.opponentId)

    const playerCreature = player?.creatures[player.activeCreatureIndex]
    const opponentCreature = opponent?.creatures[opponent.activeCreatureIndex]

    console.log(player.battleState)
    console.log(opponent.battleState)

    return (
        <div className="battle-stage">
            <h1 className="title">Battle!</h1>
            <h2 className="opponent-name">Opponent: {opponent?.name}</h2>
            <div id="player-creature">

                <HealthBar creatureData={playerCreature} />
                <h2>Player Creature</h2>
                <CreatureDisplay creatureData={playerCreature} active={true} turn={player.battleState == 'MY_TURN'}/>
            </div>
            <div id="opponent-creature">

                <HealthBar creatureData={opponentCreature} />
                <h2>Opponent Creature</h2>
                <CreatureDisplay creatureData={opponentCreature} active={false} turn={opponent.battleState == 'MY_TURN'} />
            </div>
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
