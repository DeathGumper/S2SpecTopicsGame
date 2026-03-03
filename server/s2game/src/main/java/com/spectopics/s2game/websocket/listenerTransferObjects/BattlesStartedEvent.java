package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.models.LobbyState;

public class BattlesStartedEvent {
    public LobbyState lobbyState;

    public BattlesStartedEvent(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }
}
