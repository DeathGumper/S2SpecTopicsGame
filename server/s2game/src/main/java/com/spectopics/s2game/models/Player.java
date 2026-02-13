package com.spectopics.s2game.models;

import java.util.UUID;

import lombok.Data;

@Data
public class Player {
    private String name;
    private String id;
    private Creature[] creatures;

    public Player(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.creatures = new Creature[5];
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
