package com.spectopics.s2game.models;

import java.util.HashMap;

import lombok.Data;

@Data
public class Creature {
    private String name;
    private Stats stats;
    private float totalSpeed;

    public float addTotalSpeed(float speed) {
        this.totalSpeed += speed;
        return this.totalSpeed;
    }

    public void resetTotalSpeed() {
        this.totalSpeed = 0;
        addTotalSpeed(this.stats.getSpeed());
    }

    public void NextTurn() {
        /*
        Updates variables, calls methods, and other things
         that need to be done at the start of each turn
        */

        //TODO
        /*
        Current list of what to add here:
            Damage/Heal Over Time
        */
    }



    // Creature declaration stuff
    public static HashMap<String, Creature> creatures = new HashMap<>();
    public static void AddNew(Creature creature) {
        creatures.put(creature.getName().replaceAll(" ", "").toLowerCase(), creature);
    }

    public Creature GetNew(String name) {
        return creatures.get(name).copy();
    }

    public Creature copy() {
        Creature newCreature = new Creature();
        newCreature.setName(this.name);
        newCreature.setStats(this.stats);
        return newCreature;
    }
}
