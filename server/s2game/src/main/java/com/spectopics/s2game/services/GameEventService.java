package com.spectopics.s2game.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.websocket.listenerTransferObjects.BattlesStartedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.BuyStageStartedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.CreatureBurnedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.CreatureStunnedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.GameOverEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.GetCreatureBuyOptionsEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.StatusAppliedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.LobbyJoinedEvent;
import com.spectopics.s2game.websocket.listenerTransferObjects.LobbyOwnerDisconnected;
import com.spectopics.s2game.websocket.listenerTransferObjects.ResultsStageStartedEvent;
import com.spectopics.s2game.enums.StatusNames;
import com.spectopics.s2game.models.Creature;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

/* 
 * The calls here are for calling the publisher and making the event payloads so the publisher can differentiate.
 * Only one bean so that there isnt an infinte loop!
 * Every method is pretty self explanatory, just makes the event payload and then calls the publisher.
 */
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

    public void ownerDisconnected(LobbyState lobby) throws Exception {
        publisher.publishEvent(new LobbyOwnerDisconnected(lobby));
    }

    public void battlesStarted(LobbyState lobby) {
        publisher.publishEvent(new BattlesStartedEvent(lobby));
    }

    public void resultsStageStarted(LobbyState lobby) {
        publisher.publishEvent(new ResultsStageStartedEvent(lobby));
    }

    public void sendLobbyStateToClients(LobbyState lobby) {
        publisher.publishEvent(lobby);
    }

    public void creatureBurned(Player player, Creature creature, float damage) {
        publisher.publishEvent(new CreatureBurnedEvent(player, creature, damage));
    }

    public void creatureStunned(Player player, Creature creature) {
        publisher.publishEvent(new CreatureStunnedEvent(player, creature));
    }

    public void creaturePoisoned(Player player, Creature creature) {
        publisher.publishEvent(new StatusAppliedEvent(player, creature, StatusNames.POISON));
    }

    public void creatureIgnited(Player player, Creature creature) {
        publisher.publishEvent(new StatusAppliedEvent(player, creature, StatusNames.BURN));
    }

    public void creatureStunApplied(Player player, Creature creature) {
        publisher.publishEvent(new StatusAppliedEvent(player, creature, StatusNames.STUN));
    }

    public void gameOver(Player winner) {
        publisher.publishEvent(new GameOverEvent(winner));
    }

    public void sendCreatureBuyOptions(WebSocketSession session, Creature[] options) {
        publisher.publishEvent(new GetCreatureBuyOptionsEvent(session, options));
    }
}