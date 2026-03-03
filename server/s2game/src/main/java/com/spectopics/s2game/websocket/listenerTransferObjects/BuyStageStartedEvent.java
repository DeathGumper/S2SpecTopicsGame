package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.models.LobbyState;

public class BuyStageStartedEvent {
    public LobbyState lobby;

    public BuyStageStartedEvent(LobbyState lobby) {
        this.lobby = lobby;
    }
}
