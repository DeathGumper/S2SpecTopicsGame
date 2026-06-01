import { gameController } from "../controllers/GameController"
import CreatureDisplay from "./CreatureDisplay"
import StageTimer from "./StageTimer"
import RefreshButton from "../assets/buystage/RefreshButton.png"
import ReadupButton from "../assets/buystage/ReadyUpButton.png"

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
                        <CreatureDisplay key={index} creatureData={creature} active={false} blank={creature == null} width={10}/>
                    </div>
                    
                ))}
            </div>
            
            <div id="creature-buying">
                {buyOptions != null && buyOptions.map((creature, index) => (
                    <div key={index}>
                        <button
                            className="btn-w-bg buy-creature-button menu-button glow-text-hover"
                            disabled={creature.price > player.money}
                            onClick={() => gameController.buyCreature(creature)}
                        ></button>
                        <CreatureDisplay creatureData={creature} active={false} width={10} />
                        <h2>${creature.price}</h2>
                    </div>
                ))}
            </div>
            <div id="buttons">
                <button className="btn-w-bg menu-button glow-text-hover" id="refresh" onClick={gameController.reroll}>
                    <img src={RefreshButton}></img>
                    <h2>$30</h2>
                </button>
                <button className="btn-w-bg menu-button glow-text-hover" id="ready-up" onClick={() => gameController.readyUp(lobbyState.lobbyId)}>
                    {player.ready ? "You are ready!" : "Ready Up"}
                </button>
            </div>
        </div>
    )
}

export default BuyStage