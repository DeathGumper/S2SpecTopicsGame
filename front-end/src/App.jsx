import { useState, useEffect, useRef } from 'react'
import './App.css'

import { ALLSCENES, ALLSTAGES } from './helpers/constants'
import { websocketConnection } from './controllers/WebSocketConnection'

import MainMenu from './components/MainMenu'
import TitleScreen from './components/TitleScreen'
import WebsocketLoading from './components/WebsocketLoading'
import LobbyFinding from './components/LobbyFinding'
import Lobby from './components/Lobby'
import BuyStage from './components/BuyStage'
import BattleStage from './components/BattleStage'
import EndStage from './components/EndStage'

// Custom hook to get the previous value of a state variable
function usePrevious(value) {
    const ref = useRef();
    
    // The ref is updated AFTER the component renders
    useEffect(() => {
        ref.current = value;
    }, [value]);

    // Returns the value BEFORE it was updated in useEffect
    return ref.current;
}

function App() {
    const [scene, setScene] = useState(ALLSCENES.TITLESCREEN)
    const [lobbyState, setLobbyState] = useState(null)
    const [buyOptions, setBuyOptions] = useState(null)
    const previousLobbyState = usePrevious(lobbyState);
    const [playerId, setPlayerId] = useState(null)

    useEffect(() => {
        const handler = () => {
            const updates = websocketConnection.getRecentUpdates();
            updates.forEach(({ type, payload }) => {
                if (type === 'WEBSOCKET_CONNECTED') {
                    setScene(ALLSCENES.MAINMENU);

                } else if (type === 'LOBBY_JOINED') {
                    setLobbyState(payload.lobbyState);
                    setPlayerId(payload.playerId);

                } else if (type === 'OWNER_DISCONNECTED') {
                    alert('The lobby owner has disconnected. Returning to main menu.');
                    setScene(ALLSCENES.MAINMENU);
                    setLobbyState(null);
                    setPlayerId(null);

                } else if (type === 'BUYSTAGE_STARTED') {
                    setLobbyState(payload.lobbyState);

                } else if (type === 'BATTLES_STARTED') {
                    setLobbyState(payload.lobbyState);

                } else if (type === 'RESULTSSTAGE_STARTED') {
                    setLobbyState(payload.lobbyState);

                } else if (type === 'CREATURE_STUNNED') {
                    alert(payload.creature.name + ' was stunned!');

                } else if (type === 'CREATURE_BURNED') {
                    alert(payload.creature.name + ' was burned for ' + payload.damage + ' damage!');

                } else if (type === 'CREATURE_BUY_OPTIONS') {
                    console.log(payload)
                    setBuyOptions(payload)

                } else if (type === 'CREATURE_STATUS_APPLIED') {
                    console.log(payload.creature.name + ' was affected by ' + payload.statusEffect + '!');

                } else if (type === 'UPDATE') {
                    setLobbyState(payload);
                }
            });
        };

        websocketConnection.addListener(handler);
        return () => websocketConnection.removeListener(handler);
    }, []);

    useEffect(() => {
        if (!lobbyState) return;

        if (lobbyState.stage === ALLSTAGES.LOBBY) {
            setScene(ALLSCENES.LOBBY)
        } else if (lobbyState.stage === ALLSTAGES.BUY) {
            setScene(ALLSCENES.BUY)
        } else if (lobbyState.stage === ALLSTAGES.BATTLE) {
            setScene(ALLSCENES.BATTLE)
        } else if (lobbyState.stage === ALLSTAGES.RESULTS) {
            setScene(ALLSCENES.END)
        }

    }, [lobbyState])

    var sceneComponent = null
    switch (scene) {
        case ALLSCENES.TITLESCREEN:
            sceneComponent = <TitleScreen setScene={setScene} />
            break;
        case ALLSCENES.WEBSOCKETLOADING:
            sceneComponent = <WebsocketLoading />
            break;
        case ALLSCENES.MAINMENU:
            sceneComponent = <MainMenu setScene={setScene} />
            break;
        case ALLSCENES.LOBBYFINDING:
            sceneComponent = <LobbyFinding setScene={setScene} />
            break;
        case ALLSCENES.LOBBY:
            sceneComponent = <Lobby lobbyState={lobbyState} playerId={playerId} />
            break;
        case ALLSCENES.BUY:
            sceneComponent = <BuyStage setBuyOptions={setBuyOptions} buyOptions={buyOptions} lobbyState={lobbyState} playerId={playerId} />
            break;
        case ALLSCENES.BATTLE:
            sceneComponent = <BattleStage lobbyState={lobbyState} playerId={playerId} />
            break;
        case ALLSCENES.END:
            sceneComponent = <EndStage lobbyState={lobbyState} playerId={playerId} />
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
