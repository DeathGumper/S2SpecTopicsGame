import StageTimer from "./StageTimer"

function EndStage({ lobbyState, playerId }) {
    // there shouldnt be a endscreen w/o lobby state
    // But if a bug happens, i dont want crash.
    if (lobbyState == null) {
        return (
            <div>
                
            </div>
        )
    }

    // Get player
    const player = lobbyState?.players?.find(p => p.id === playerId)
    
    // Show the player that the battle ended.
    return (
        <div className="end-stage">
            <StageTimer serverTime={lobbyState.stageTimer} />
            <h1 className="title">End Stage</h1>
            {/* <h3>{player?.battleStage === "WON" ? "You won this battle!" : "You lost this battle!"}</h3> */}
        </div>
    )
}

export default EndStage