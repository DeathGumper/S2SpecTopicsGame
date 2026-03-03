package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.LobbyState;

public class LobbyJoinedPayload {
    public LobbyState lobbyState;
    public String playerId;

    public LobbyJoinedPayload(LobbyState lobbyState, String playerId) {
        this.lobbyState = lobbyState;
        this.playerId = playerId;
    }
}
