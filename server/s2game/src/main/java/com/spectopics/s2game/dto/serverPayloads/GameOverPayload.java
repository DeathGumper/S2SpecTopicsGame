package com.spectopics.s2game.dto.serverPayloads;

public class GameOverPayload {
    // Tells the clients the game is over, and who won.
    public String result;

    public GameOverPayload(String result) {
        this.result = result;
    }
}
