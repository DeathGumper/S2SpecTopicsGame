package com.spectopics.s2game.models;

import lombok.Data;

@Data
public class Battle {
    private Player player1;
    private Player player2;

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
}
