package com.spectopics.dto;

import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

public class LobbyingDto {
    public LobbyState lobbyState;
    public Player player;

    public LobbyingDto(LobbyState lobbyState, Player player) {
        this.lobbyState = lobbyState;
        this.player = player;
    }
}
