package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.LobbyState;

public class ResultsStageStartedPayload {
    // Battle over, tells all clients the results stage has started, and gives them the current state of the lobby.
    public LobbyState lobbyState;

    public ResultsStageStartedPayload(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }
}
