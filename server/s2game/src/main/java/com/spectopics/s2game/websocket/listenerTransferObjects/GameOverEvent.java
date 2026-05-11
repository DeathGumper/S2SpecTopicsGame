package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.models.Player;

public class GameOverEvent {
    public Player winner;

    public GameOverEvent(Player winner) {
        this.winner = winner;
    }
}
