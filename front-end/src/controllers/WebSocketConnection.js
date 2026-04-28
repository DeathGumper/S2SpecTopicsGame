import { LobbyState } from '../models/LobbyState.js';
import { LobbyJoinedPayload } from '../dto/serverPayloads/LobbyJoinedPayload.js';
import { BuyStageStartedPayload } from '../dto/serverPayloads/BuyStageStartedPayload.js';
import { BattlesStartedPayload } from '../dto/serverPayloads/BattlesStartedPayload.js';
import { ResultsStageStartedPayload } from '../dto/serverPayloads/ResultsStageStartedPayload.js';

const PRODUCTION_URL = 'https://s2project.azurewebsites.net';

class WebSocketConnection {
    constructor() {
        this.websocket = null;
        this._queue = [];
        this._listeners = [];
    }

    connect() {
        try {
            const { hostname, port } = window.location;
            const isLocal = hostname === 'localhost' || hostname === '127.0.0.1';
            const uri = isLocal
                ? `ws://${hostname}:8080/websocket`
                : `${PRODUCTION_URL}/websocket`;

            this.websocket = new WebSocket(uri);
            this.websocket.onopen = () => console.log('Connected to server websocket.');
            this.websocket.onclose = () => console.log('WebSocket closed.');
            this.websocket.onerror = () => console.log('WebSocket error.');
            this.websocket.onmessage = (event) => {
                const parsed = this._parseMessage(event.data);
                if (parsed) {
                    this._queue.push(parsed);
                    this._listeners.forEach(fn => fn(parsed));
                }
            };
        } catch (e) {
            console.log('An error occurred connecting to WebSocket: ' + e);
        }
    }

    sendMessage(message) {
        if (!this.websocket || this.websocket.readyState !== WebSocket.OPEN) {
            console.log('WebSocket not connected.');
            return;
        }
        try {
            this.websocket.send(JSON.stringify(message));
            console.log('Sent message: ', message);
        } catch (e) {
            console.log('Failed to send message: ' + e);
        }
    }

    getRecentUpdates() {
        const updates = [...this._queue];
        this._queue = [];
        return updates;
    }

    addListener(fn) {
        this._listeners.push(fn);
    }

    removeListener(fn) {
        this._listeners = this._listeners.filter(l => l !== fn);
    }

    _parseMessage(raw) {
        try {
            const json = JSON.parse(raw);
            const { type, payload } = json;

            switch (type) {
                case 'WEBSOCKET_CONNECTED':
                    return { type, payload };

                case 'LOBBY_JOINED':
                    return { type, payload: LobbyJoinedPayload.fromDict(payload) };

                case 'BUYSTAGE_STARTED':
                    return { type, payload: BuyStageStartedPayload.fromDict(payload) };

                case 'BATTLES_STARTED':
                    return { type, payload: BattlesStartedPayload.fromDict(payload) };

                case 'RESULTSSTAGE_STARTED':
                    return { type, payload: ResultsStageStartedPayload.fromDict(payload) };

                case 'CREATURE_STUNNED':
                    return { type, payload };
                
                case 'CREATURE_BURNED':
                    // Payload contains damage from burn
                    return { type, payload };

                case 'UPDATE':
                    return { type, payload: LobbyState.fromDict(payload) };

                default:
                    console.log('Type ' + type + ' was not recognized.');
                    return null;
            }
        } catch (e) {
            console.log('Failed to parse message: ' + e);
            return null;
        }
    }
}

export const websocketConnection = new WebSocketConnection();
