package com.spectopics.s2game.dto.clientPayloads;

public class JoinLobbyPayload {
    public String playerName;
    public String lobbyId;

    public JoinLobbyPayload(String playerName, String lobbyId) {
        this.playerName = playerName;
        this.lobbyId = lobbyId;
    }
}
