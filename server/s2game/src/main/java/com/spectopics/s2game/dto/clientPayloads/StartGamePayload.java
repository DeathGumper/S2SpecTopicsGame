package com.spectopics.s2game.dto.clientPayloads;

public class StartGamePayload {
    // Only sent from the owner of the lobby.
    public String lobbyId;

    public StartGamePayload(String lobbyId) {
        this.lobbyId = lobbyId;
    }
}
