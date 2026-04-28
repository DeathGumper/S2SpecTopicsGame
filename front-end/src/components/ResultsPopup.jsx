function ResultsPopup({ player }) {
    return (
        <div className="results-popup">
            <h1>Results</h1>
            <p>{player.battleState == 'won' ? 'You won!' : 'You lost.'}</p>
            <p>{player.name} has {player.lives} lives remaining.</p>
            <p>Waiting for other battles to finish...</p>
        </div>
    )
}