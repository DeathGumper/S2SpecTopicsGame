package com.spectopics.s2game.websocket.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.spectopics.s2game.dto.serverPayloads.LobbyJoinedPayload;
import com.spectopics.s2game.websocket.handlers.SocketConnectionHandler;
import com.spectopics.s2game.websocket.listenerTransferObjects.GameStartedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.LobbyJoinedEvent;

@Component
public class SocketEventListener {

    private final SocketConnectionHandler socketHandler;

    public SocketEventListener(SocketConnectionHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @EventListener
    public void handleGameStarted(GameStartedEvent event) throws Exception {
        // Broadcasts to the lobby the new lobbyState and the type of game started
        socketHandler.broadcastToLobby(
            event.lobby,
            "GAME_STARTED",
            event.lobby
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
}