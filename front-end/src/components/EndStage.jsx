import StageTimer from "./StageTimer"

function EndStage({ lobbyState, playerId }) {

    const player = lobbyState?.players.find(p => p.id === playerId)

    console.log(player.battleStage)
    
    return (
        <div className="end-stage">
            <StageTimer serverTime={lobbyState.stageTimer} />
            <h1 className="title">End Stage</h1>
            {/* <h3>{player?.battleStage == "WON" ? "You won!" : "You lost!"}</h3> */}
        </div>
    )
}

export default EndStage