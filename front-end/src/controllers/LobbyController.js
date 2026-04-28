import { websocketConnection } from "./WebSocketConnection";
import { ClientMessage } from "../dto/ClientMessage.js";
import { CreateLobbyPayload } from "../dto/clientPayloads/CreateLobbyPayload.js";
import { JoinLobbyPayload } from "../dto/clientPayloads/JoinLobbyPayload.js";

function LobbyController() {
    this.createLobby = function(lobbyId, playerName) {
        websocketConnection.sendMessage(new ClientMessage('CREATE_LOBBY', new CreateLobbyPayload(playerName, lobbyId)));
    };

    this.joinLobby = function(lobbyId, playerName) {
        websocketConnection.sendMessage(new ClientMessage('JOIN_LOBBY', new JoinLobbyPayload(playerName, lobbyId)));
    }
}

export const lobbyController = new LobbyController();
