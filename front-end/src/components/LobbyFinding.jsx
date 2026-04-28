import { lobbyController } from "../controllers/LobbyController";
import { ALLSCENES } from "../helpers/constants";

import '../styles/LobbyFindingStyles.css'

function LobbyFinding({ setScene }) {
    return (
        <div id="lobby-finding">
            <h1>Lobby Finder</h1>
            <div className="back-button" onClick={() => setScene(ALLSCENES.MAINMENU)}>
                <span className="arrow"></span>
                <span className="text glow-text-hover">Back</span>
            </div>
            <div id="buttons">
                <button className="menu-button glow-text-hover" onClick={() => lobbyController.joinLobby(document.getElementById('id').value, document.getElementById('username').value)}>Join Lobby</button>
                <button className="menu-button glow-text-hover" onClick={() => lobbyController.createLobby(document.getElementById('id').value, document.getElementById('username').value)}>Create Lobby</button>
            </div>
            <div id="inputs">
                <input className="menu-button glow-text-hover" id="id" type="text" placeholder="Lobby Id"/>
                <input className="menu-button glow-text-hover" id="username" type="text" placeholder="Username"/>
            </div>
            
        </div>
    )
}

export default LobbyFinding