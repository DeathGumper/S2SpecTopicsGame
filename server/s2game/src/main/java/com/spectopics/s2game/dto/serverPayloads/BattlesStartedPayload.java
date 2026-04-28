package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.LobbyState;

public class BattlesStartedPayload {
    public LobbyState lobbyState;

    public BattlesStartedPayload(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }
}
