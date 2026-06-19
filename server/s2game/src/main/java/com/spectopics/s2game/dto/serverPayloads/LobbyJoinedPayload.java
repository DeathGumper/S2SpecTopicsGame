package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.LobbyState;

public class LobbyJoinedPayload {
    // Tells the clients that someone has successfully joined the lobby, and gives them the current state of the lobby.
    public LobbyState lobbyState;
    public String playerId;

    public LobbyJoinedPayload(LobbyState lobbyState, String playerId) {
        this.lobbyState = lobbyState;
        this.playerId = playerId;
    }
}
