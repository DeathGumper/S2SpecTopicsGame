import { ALLSCENES } from "../helpers/constants";

function GameOverScreen({ gameResult, setScene }) {
    return (
        <div>
            <h1 className="title">{gameResult === "WON" ? "You WON!": "You lost!"}</h1>
            <button className="menu-button glow-text-hover" onClick={() => setScene(ALLSCENES.MAINMENU)}>Back to Lobby Finding</button>
        </div>
    )
}

export default GameOverScreen;