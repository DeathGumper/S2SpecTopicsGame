package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.models.LobbyState;

public class GameStartedEvent {
    public LobbyState lobby;

    public GameStartedEvent(LobbyState lobby) {
        this.lobby = lobby;
    }
}
