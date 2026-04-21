function BattleStage({ setScene }) {
    return (
        <div className="battle-stage">
            <h1>Battle Stage</h1>
            <div id="health-bars">
                <div id="player-health">
                    <h2>Player Health</h2>
                    <div id="player-health-bar"></div>
                </div>
                <div id="opponent-health">
                    <h2>Opponent Health</h2>
                    <div id="opponent-health-bar"></div>
                </div>
            </div>
        </div>
    )
}

export default BattleStage