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

    public void NextTurn() {
        // This is temporary, it doenst implement the system of next turn is the creature with the lowest speed
        if (this.state == BattleState.PLAYER1) this.state = BattleState.PLAYER2;
        else this.state = BattleState.PLAYER1;
    }
    
    public void NextP1Creature() {
        if (this.player1.NextCreature() == false)
            state = BattleState.DONE;
    }

    public void NextP2Creature() {
        if (this.player2.NextCreature() == false)
            state = BattleState.DONE;
    }

    public boolean isBattleDone() {
        return state == BattleState.DONE;
    }

    public BattleState GetBattleState() {
        return state;
    }

    public Player GetPlayer1() {
        return player1;
    }

    public Player GetPlayer2() {
        return player2;
    }
}
