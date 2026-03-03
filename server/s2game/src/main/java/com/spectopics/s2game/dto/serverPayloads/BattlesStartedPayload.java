package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.LobbyState;

public class BattlesStartedPayload {
    public LobbyState lobbyState;
    public String battleId; // The id of the battle this client is in

    public BattlesStartedPayload(LobbyState lobbyState, String battleId) {
        this.lobbyState = lobbyState;
        this.battleId = battleId;
    }
}
