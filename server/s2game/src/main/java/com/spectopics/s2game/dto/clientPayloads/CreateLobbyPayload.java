package com.spectopics.s2game.dto.clientPayloads;

public class CreateLobbyPayload {
    public String playerName;
    public String lobbyId;

    public CreateLobbyPayload(String playerName, String lobbyId) {
        this.playerName = playerName;
        this.lobbyId = lobbyId;
    }
}
