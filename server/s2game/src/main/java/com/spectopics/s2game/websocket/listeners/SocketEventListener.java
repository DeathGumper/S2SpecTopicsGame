package com.spectopics.s2game.websocket.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.dto.serverPayloads.BattlesStartedPayload;
import com.spectopics.s2game.dto.serverPayloads.BuyStageStartedPayload;
import com.spectopics.s2game.dto.serverPayloads.LobbyJoinedPayload;
import com.spectopics.s2game.dto.serverPayloads.ResultsStageStartedPayload;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;
import com.spectopics.s2game.services.BattleService;
import com.spectopics.s2game.websocket.handlers.SocketConnectionHandler;
import com.spectopics.s2game.websocket.listenerTransferObjects.BattlesStartedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.BuyStageStartedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.LobbyJoinedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.ResultsStageStartedEvent;

@Component
public class SocketEventListener {

    private final SocketConnectionHandler socketHandler;

    public SocketEventListener(SocketConnectionHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @EventListener
    public void handleBuyStageStarted(BuyStageStartedEvent event) throws Exception {
        // Broadcasts to the lobby the new lobbyState and the type of game started
        socketHandler.broadcastToLobby(
            event.lobby,
            "BUYSTAGE_STARTED",
            new BuyStageStartedPayload(event.lobby)
        );
    }

    @EventListener
    public void handleLobbyJoined(LobbyJoinedEvent event) throws Exception {
        socketHandler.broadcastToIndividual(
            event.session,
            "LOBBY_JOINED",
            new LobbyJoinedPayload(event.lobby, event.playerId)
        );
    }

    @EventListener
    public void handleBattlesStarted(BattlesStartedEvent event) throws Exception {
        for (Player player : event.lobbyState.getPlayers()) {
            WebSocketSession session = player.getSession();
            String battleId = BattleService.getBattleByPlayerId(event.lobbyState.getBattles(), session.getId()).getId();
            socketHandler.broadcastToIndividual(
                session, 
                "BATTLES_STARTED", 
                new BattlesStartedPayload(event.lobbyState, battleId)
            );

        }
    }

    @EventListener
    public void handleResultStageStarted(ResultsStageStartedEvent event) throws Exception {
        socketHandler.broadcastToLobby(
            event.lobby,
            "RESULTSSTAGE_STARTED",
            new ResultsStageStartedPayload(event.lobby)
        );
    }

    @EventListener
    public void handleLobbyStateSend(LobbyState lobby) throws Exception {
        System.out.println("Update sent to clients");
        socketHandler.broadcastToLobby(
            lobby, 
            "UPDATE", 
            lobby
        );
    }
}