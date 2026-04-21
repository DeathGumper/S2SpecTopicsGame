import MainMenu from './components/MainMenu'
import './App.css'

import { useState } from 'react'
import { ALLSCENES } from './helpers/constants'
import LobbyFinding from './components/LobbyFinding'

function App() {
  var [scene, setScene] = useState(ALLSCENES.MAINMENU)
  var sceneComponent = null

  // case switch with the scene
  switch (scene) {
    case ALLSCENES.MAINMENU:
        sceneComponent = <MainMenu setScene={ setScene }/>
        break;
    case ALLSCENES.LOBBYFINDING:
        sceneComponent = <LobbyFinding setScene={ setScene }/>
        break;
    default:
        break;
  }

  return (
    <div className="app">
        {sceneComponent}
    </div>
  )
}

export default App
