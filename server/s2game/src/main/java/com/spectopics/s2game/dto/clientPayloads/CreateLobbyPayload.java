package com.spectopics.s2game.dto.clientPayloads;

public class CreateLobbyPayload {
    // Sent from the client when creating a lobby, 
    // contains all the info the server needs to create a lobby and add the player to it
    public String playerName;
    public String lobbyId;

    public CreateLobbyPayload(String playerName, String lobbyId) {
        this.playerName = playerName;
        this.lobbyId = lobbyId;
    }
}
