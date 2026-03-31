package com.spectopics.s2game.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.websocket.listenerTransferObjects.BattlesStartedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.BuyStageStartedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.LobbyJoinedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.ResultsStageStartedEvent;
import com.spectopics.s2game.models.LobbyState;

@Service
public class GameEventService {
    private ApplicationEventPublisher publisher;

    private GameEventService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;

        System.out.println("GameEventService initialized with ApplicationEventPublisher");
    }

    public void buyStageStarted(LobbyState lobby) throws Exception {
        publisher.publishEvent(new BuyStageStartedEvent(lobby));
    }

    public void lobbyJoined(LobbyState lobby, WebSocketSession session, String playerId) throws Exception {
        publisher.publishEvent(new LobbyJoinedEvent(lobby, session, playerId));
    }

    public void battlesStarted(LobbyState lobby) {
        publisher.publishEvent(new BattlesStartedEvent(lobby));
    }

    public void resultsStageStarted(LobbyState lobby) {
        publisher.publishEvent(new ResultsStageStartedEvent(lobby));
    }

    public void sendLobbyStateToClients(LobbyState lobby) {
        publisher.publishEvent(lobby);

        System.out.println("sent lobbystate from ges");
    }
}