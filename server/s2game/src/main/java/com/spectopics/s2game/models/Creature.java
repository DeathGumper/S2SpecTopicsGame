package com.spectopics.s2game.models;
 
 
import java.util.Map;

import lombok.Data;
 
@Data
public class Creature {
    private String name;
    private Stats stats;
    private Map<String, String> abilities;
    private float totalSpeed;
 
    public float addTotalSpeed(float speed) {
        this.totalSpeed += speed;
        return this.totalSpeed;
    }
 
    public void resetTotalSpeed() {
        this.totalSpeed = 0;
        addTotalSpeed(this.stats.getSpeed());
    }
 
 
 
    public Creature copy() {
        Creature newCreature = new Creature();
        newCreature.setName(this.name);
        newCreature.setStats(this.stats);
        return newCreature;
    }
}