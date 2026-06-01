import { websocketConnection } from "./WebSocketConnection";
import { ClientMessage } from "../dto/ClientMessage.js";
import { ReadyUpPayload } from "../dto/clientPayloads/ReadyUpPayload.js";
import { StartGamePayload } from "../dto/clientPayloads/StartGamePayload.js";
import { ActionPayload } from "../dto/clientPayloads/ActionPayload.js";

function GameController() {
    this.startGame = function(lobbyId) {
        websocketConnection.sendMessage(new ClientMessage('START_GAME', new StartGamePayload(lobbyId)));
    }
    this.readyUp = function(lobbyId) {
        websocketConnection.sendMessage(new ClientMessage('READY_UP', new ReadyUpPayload(lobbyId)));
    };

    this.buyCreature = function(creature) {
        websocketConnection.sendMessage(new ClientMessage('BUY_CREATURE', creature));
    };

    this.endBattleStage = function(lobbyId) {
        websocketConnection.sendMessage(new ClientMessage('END_BATTLES', lobbyId));
    };

    this.useAbility = function(abilityId) {
        websocketConnection.sendMessage(new ClientMessage('CALL_ACTION', new ActionPayload(abilityId)));
    }

    this.getCreatureBuyOptions = function() {
        websocketConnection.sendMessage(new ClientMessage('GET_CREATURE_BUY_OPTIONS', null))
    }

    this.reroll = function() {
        websocketConnection.sendMessage(new ClientMessage('GET_CREATURE_BUY_OPTIONS', "true"))
    }

}

export const gameController = new GameController();
