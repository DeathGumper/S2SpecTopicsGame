package com.spectopics.s2game.models;

import lombok.Data;

@Data
public class LobbySettings {
    private float buyStageTimer = 100f; // timer of each buystage
    private float resultStageTimer = 10f; // time of each results screen stage
    private float turnTimer = 30f; // timer of each turn
    private int maxPlayers = 2; // max players in a lobby
}
