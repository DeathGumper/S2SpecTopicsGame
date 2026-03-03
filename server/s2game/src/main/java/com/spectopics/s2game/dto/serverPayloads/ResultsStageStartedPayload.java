package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.LobbyState;

public class ResultsStageStartedPayload {
    public LobbyState lobbyState;

    public ResultsStageStartedPayload(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }
}
