import { gameController } from "../controllers/GameController"
import CreatureDisplay from "./CreatureDisplay"
import StageTimer from "./StageTimer"

function BuyStage({ lobbyState, playerId }) {
    const player = lobbyState?.players.find(p => p.id === playerId)

    return (
        <div id="buy-stage">
            <StageTimer serverTime={lobbyState.stageTimer} />
            <h1 className="title">Buy Stage!</h1>
            <p>Lives: {player.lives}</p>
            <div id="creature-display">
                {player.creatures.map((creature, index) => (
                    <div key={index}>
                        {creature ? (
                            <CreatureDisplay key={index} creatureData={creature} active={false} />
                        ) : (
                            <p key={index}>Empty Slot</p>
                        )}
                    </div>
                    
                ))}
            </div>
            <button className="menu-button glow-text-hover" onClick={() => gameController.buyCreature()}>Buy Creature</button>
            <button className="menu-button glow-text-hover" onClick={() => gameController.readyUp(lobbyState.lobbyId)}>Ready Up</button>
        </div>
    )
}

export default BuyStage