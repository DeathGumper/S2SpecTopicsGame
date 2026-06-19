import { gameController } from "../controllers/GameController"

import '../styles/LobbyStyles.css'

function Lobby({ lobbyState, playerId }) {
    // This scene displays all of the players in the lobby
    const player = lobbyState?.players.find(p => p.id === playerId)

    // Visuals
    // Displays the players in a list
    // Displays to the owner of the lobby only if the lobby is ready to start.
    return (
        <div id="lobby-scene">
            <h1 id="lobby-name">{lobbyState.name}</h1>
            <div id="players-list">
                {lobbyState.players.map(player => (
                    <p key={player.id}>{player.owner ? "(Host) ": ""} {player.name} {player.id === playerId ? " (You)" : ""}</p>
                ))}
            </div>
            <p>{lobbyState.players.length >= 2 ? "Ready to start!" : "Waiting for more players..."}</p>
            {player.owner && <button id="start-game" className="menu-button glow-text-hover" onClick={() => gameController.startGame(lobbyState.id)}>Start Game</button>}
        </div>
    )
}

export default Lobby