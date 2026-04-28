import { ALLSCENES } from "../helpers/constants"
import { websocketConnection } from "../controllers/WebSocketConnection"

import '../styles/TitleScreenStyles.css'

function TitleScreen({ setScene }) {
    function handleStartGame() {
        setScene(ALLSCENES.WEBSOCKETLOADING);
        websocketConnection.connect();
    }

    return (
        <div id="title-screen">
            <h1>Creature Battles</h1>
            <button className="glow-text-hover menu-button" onClick={handleStartGame}>Start Game</button>
        </div>
    )
}

export default TitleScreen