package com.spectopics.s2game.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.websocket.listenerTransferObjects.GameStartedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.LobbyJoinedEvent;
import com.spectopics.s2game.models.LobbyState;

@Service
public class GameEventService {
    private ApplicationEventPublisher publisher;

    private GameEventService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;

        System.out.println("GameEventService initialized with ApplicationEventPublisher");
    }

    public void gameStarted(LobbyState lobby) throws Exception {
        publisher.publishEvent(new GameStartedEvent(lobby));
    }

    public void lobbyJoined(LobbyState lobby, WebSocketSession session, String playerId) throws Exception {
        publisher.publishEvent(new LobbyJoinedEvent(lobby, session, playerId));
    }
}