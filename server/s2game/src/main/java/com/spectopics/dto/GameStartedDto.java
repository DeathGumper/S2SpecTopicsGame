package com.spectopics.dto;

import com.spectopics.s2game.models.LobbyState;

public class GameStartedDto {
    public LobbyState lobbyState;
    public boolean hasGameStarted;

    public GameStartedDto(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
        this.hasGameStarted = true;
    }
}
