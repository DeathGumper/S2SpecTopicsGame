import { ALLSCENES } from './helpers/constants'

function MainMenu({ setScene}) {
    return (
        <div className="main-menu">
            <h1>Main Menu</h1>
            <button onClick={() => setScene(ALLSCENES.LOBBYFINDING)}>Play</button>
            <button>Options</button>
        </div>
    )
}

export default MainMenu