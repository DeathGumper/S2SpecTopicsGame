package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.LobbyState;

public class BuyStageStartedPayload {
    // Tells all the clients the buy stage has started.
    public LobbyState lobbyState;

    public BuyStageStartedPayload(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }
}
