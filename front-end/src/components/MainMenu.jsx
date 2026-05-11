import { ALLSCENES } from '../helpers/constants'

import '../styles/MainMenuStyles.css'

function MainMenu({ setScene}) {
    return (
        <div id="main-menu">
            <h1>Main Menu</h1>
            <div id="buttons">
                <button className="menu-button glow-text-hover" onClick={() => setScene(ALLSCENES.LOBBYFINDING)}>Play</button>
                <button className="menu-button glow-text-hover" onClick={() => setScene(ALLSCENES.INSTRUCTIONS)}>How to Play</button>
                <button className="menu-button glow-text-hover">Options</button>
            </div>
            
        </div>
    )
}

export default MainMenu