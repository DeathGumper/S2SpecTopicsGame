package com.spectopics.s2game.dto.clientPayloads;

public class ReadyUpPayload {
    // Sent from client, just the lobby id
    public String lobbyId;

    public ReadyUpPayload(String lobbyId) {
        this.lobbyId = lobbyId;
    }
}
