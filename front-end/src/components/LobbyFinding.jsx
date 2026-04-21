import { ALLSCENES } from "../helpers/constants";

function LobbyFinding({ setScene }) {
    return (
        <div className="lobby-finding">
            <h1>Lobby Finder</h1>
            <div id="buttons">
                <button onClick={() => setScene(ALLSCENES.MAINMENU)}>Back to Main Menu</button>
                <button onClick={() => setScene(ALLSCENES.JOINLOBBY)}>Join Lobby</button>
            </div>
            <div id="inputs">
                <input id="id" type="text" placeholder="Lobby Id"/>
                <input id="username" type="text" placeholder="Username"/>
            </div>
            
        </div>
    )
}

export default LobbyFinding