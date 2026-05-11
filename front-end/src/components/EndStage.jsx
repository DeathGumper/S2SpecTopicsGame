import StageTimer from "./StageTimer"

function EndStage({ lobbyState, playerId }) {
    if (lobbyState == null) {
        return (
            <div>
                
            </div>
        )
    }

    const player = lobbyState?.players?.find(p => p.id === playerId)
    
    return (
        <div className="end-stage">
            <StageTimer serverTime={lobbyState.stageTimer} />
            <h1 className="title">End Stage</h1>
            {/* <h3>{player?.battleStage === "WON" ? "You won this battle!" : "You lost this battle!"}</h3> */}
        </div>
    )
}

export default EndStage