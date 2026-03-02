package com.spectopics.s2game.websocket.listenerTransferObjects;

import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.models.LobbyState;

public class LobbyJoinedEvent {
    public LobbyState lobby;
    public WebSocketSession session;
    public String playerId;

    public LobbyJoinedEvent(LobbyState lobby, WebSocketSession session, String playerId) {
        this.lobby = lobby;
        this.session = session;
        this.playerId = playerId;
    }
}
