package com.spectopics.s2game.models;
import java.util.List;

import lombok.Data;

/**
 * Docstring for com.spectopics.s2game.models.GameState
 * 
 * Core state of the game. Will hold all relevant information regarding the current state of the game; turn, players in current battle, etc.
 */

@Data
public class GameState {
    private Creature turn;
    private Player[] players = new Player[2];
    private List<Creature> creatureOrder;

    public GameState(Player player1, Player player2) {
        this.players[0] = player1;
        this.players[1] = player2;

        
    }
    
    private int GetCreatureSpot(Creature creature) {
        float total = creature.getTotalSpeed();

        int spot = 0; // The spot where the creature belongs.

        for (int i = 0; i < creatureOrder.size(); i++) {
            if (creatureOrder.get(i).getTotalSpeed() > total) {
                spot = i;
            }
        }
        return spot;
    }

    public Creature NextCreature() {
        Creature currentCreature = creatureOrder.get(0);
        currentCreature.addTotalSpeed(currentCreature.getStats().getSpeed());
        int spot = GetCreatureSpot(currentCreature);

        creatureOrder.add(spot, currentCreature);
        currentCreature = creatureOrder.remove(0);

        return currentCreature;
    }
}
