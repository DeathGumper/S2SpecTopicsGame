package com.spectopics.s2game.models;

import lombok.Data;

// easy spot to add/change settings for dev.
// maybe could be part of a settings menu.

@Data
public class LobbySettings {
    private float buyStageTimer = 300f; // timer of each buystage
    private float resultStageTimer = 5f; // time of each results screen stage
    private float turnTimer = 30f; // timer of each turn
    private int maxPlayers = 2; // max players in a lobby
}
