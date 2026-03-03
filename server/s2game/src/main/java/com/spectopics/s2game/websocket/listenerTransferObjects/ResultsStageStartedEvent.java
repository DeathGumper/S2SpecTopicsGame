package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.models.LobbyState;

public class ResultsStageStartedEvent {
    public LobbyState lobby;

    public ResultsStageStartedEvent(LobbyState lobbyState) {
        this.lobby = lobbyState;
    }
}
