package com.spectopics.s2game.models;

import com.spectopics.s2game.enums.BattleState;

import lombok.Data;

@Data
public class Battle {
    private String id;
    private Player player1;
    private Player player2;

    private BattleState state = BattleState.PLAYER1;

    public Battle(Player player1, Player player2) {
        // set uuid
        id = java.util.UUID.randomUUID().toString();
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isBattleDone() {
        return state == BattleState.DONE;
    }
}
