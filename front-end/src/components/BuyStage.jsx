import { gameController } from "../controllers/GameController"
import CreatureDisplay from "./CreatureDisplay"
import StageTimer from "./StageTimer"

import '../styles/BuyStageStyles.css'

function BuyStage({ buyOptions, lobbyState, playerId }) {
    const player = lobbyState?.players.find(p => p.id === playerId)

    if (buyOptions == null) {
        gameController.getCreatureBuyOptions()
    }

    return (
        <div id="buy-stage">
            <h1 className="title">Buy Stage!</h1>
            <div id="information">
                <StageTimer serverTime={lobbyState.stageTimer} />
                <p>Lives: {player.lives}</p>
                <p>Money: ${player.money}</p>
            </div>
            
            <div id="creature-display">
                {player.creatures.map((creature, index) => (
                    <div key={index}>
                        <CreatureDisplay key={index} creatureData={creature} active={false} blank={creature == null}/>
                    </div>
                    
                ))}
            </div>
            
            <div id="creature-buying">
                {buyOptions != null && buyOptions.map((creature, index) => (
                    <div key={index}>
                        {creature.price <= player.money ? <button className="menu-button glow-text-hover" onClick={() => gameController.buyCreature(creature)}>Buy Creature!</button> : <h2>Not enough money!</h2>}
                        <CreatureDisplay creatureData={creature} active={false} />
                        <h2>${creature.price}</h2>
                    </div>
                ))}
            </div>
            <div id="buttons">
                <button className="menu-button glow-text-hover" id="refresh" onClick={gameController.getCreatureBuyOptions}>Refresh!</button>
                <button className="menu-button glow-text-hover" id="ready-up" onClick={() => gameController.readyUp(lobbyState.lobbyId)}>{player.ready ? "You are ready!" : "Ready Up!"}</button>
            </div>
        </div>
    )
}

export default BuyStage