package com.spectopics.s2game.dto.clientPayloads;

public class JoinLobbyPayload {
    // Sent from the client when joining a lobby,
    // contains all the info the server needs to add the player to the lobby
    // and make the player
    public String playerName;
    public String lobbyId;

    public JoinLobbyPayload(String playerName, String lobbyId) {
        this.playerName = playerName;
        this.lobbyId = lobbyId;
    }
}
