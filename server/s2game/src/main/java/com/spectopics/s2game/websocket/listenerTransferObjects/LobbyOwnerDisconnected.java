package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.models.LobbyState;

public class LobbyOwnerDisconnected {
    public LobbyState lobbyState;

    public LobbyOwnerDisconnected(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }
}
