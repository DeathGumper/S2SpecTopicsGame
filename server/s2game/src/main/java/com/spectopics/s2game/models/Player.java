package com.spectopics.s2game.models;

import lombok.Data;

@Data
public class Player {
    private String name;
    private Creature[] creatures = new Creature[5];

    public Player(String name) {
        this.name = name;
    }

    public boolean AddCreature(Creature creature) {
        for (int i = 0; i < this.creatures.length; i++) {
            if (this.creatures[i] == null) {
                this.creatures[i] = creature;
                return true;
            }
        }

        return false;
    }
}
