import { gameController } from "../controllers/GameController"
import CreatureDisplay from "./CreatureDisplay"
import StageTimer from "./StageTimer"

import '../styles/BuyStageStyles.css'

function buyCreature(setBuyOptions, creature) {
    gameController.buyCreature(creature)
    setBuyOptions(null)
    gameController.getCreatureBuyOptions()
}

function BuyStage({ buyOptions, setBuyOptions, lobbyState, playerId }) {
    const player = lobbyState?.players.find(p => p.id === playerId)

    if (buyOptions == null) {
        gameController.getCreatureBuyOptions()
    }

    return (
        <div id="buy-stage">
            <StageTimer serverTime={lobbyState.stageTimer} />
            <h1 className="title">Buy Stage!</h1>
            <p>Lives: {player.lives}</p>
            <div id="creature-display">
                {player.creatures.map((creature, index) => (
                    <div key={index}>
                        <CreatureDisplay key={index} creatureData={creature} active={false} blank={creature == null}/>
                    </div>
                    
                ))}
            </div>
            <button className="menu-button glow-text-hover" id="refresh" onClick={gameController.getCreatureBuyOptions}>Refresh!</button>
            <div id="creature-buying">
                {buyOptions != null && buyOptions.map((creature, index) => (
                    <div key={index}>
                        <button className="menu-button glow-text-hover" onClick={() => buyCreature(setBuyOptions, creature)}>Buy Creature!</button>
                        <CreatureDisplay creatureData={creature} active={false} />
                        
                    </div>
                ))}
            </div>
            <button className="menu-button glow-text-hover" onClick={() => gameController.readyUp(lobbyState.lobbyId)}>Ready Up</button>
        </div>
    )
}

export default BuyStage