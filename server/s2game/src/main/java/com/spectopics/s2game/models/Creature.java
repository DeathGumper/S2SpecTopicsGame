package com.spectopics.s2game.models;
 
 
import java.util.Map;

import tools.jackson.databind.ObjectMapper;

import lombok.Data;
 
@Data
public class Creature {
    private String name;
    private Stats stats;
    private Map<String, String> abilities;
    private String[] effects;
    // private float totalSpeed;
 
    // public float addTotalSpeed(float speed) {
    //     this.totalSpeed += speed;
    //     return this.totalSpeed;
    // }
 
    // public void resetTotalSpeed() {
    //     this.totalSpeed = 0;
    //     addTotalSpeed(this.stats.getSpeed());
    // }

    public boolean checkStun() {
        float stunChance = (float) (Math.random() * 100);
        if (stunChance < this.stats.getStun()) {
            System.out.println(this.name + " is stunned!");
            this.stats.setStun(0);
            return true;
        }
        return false;
    }

    public float takeBurn() {
        if (this.stats.getBurn() > 0) {
            float burnDamage = this.stats.getBurn() * 100 * this.getPoisonDamageMultiplier();
            System.out.println(this.name + " takes " + burnDamage + " burn damage!");
            this.stats.AdjustHealth(-burnDamage);
            this.stats.setBurn((float) Math.floor(this.stats.getBurn() / 2));
            return burnDamage;
        }
        return 0;
    }

    public float getPoisonDamageMultiplier() {
        if (this.stats.getPoison() > 0) {
            return 1 + (this.stats.getPoison() / 10);
        }
        return 1;
    }

    public void resetStatusEffects() {
        this.stats.setBurn(0);
        this.stats.setPoison(0);
        this.stats.setStun(0);
    }
 
    public void resetHealth() {
        this.stats.SetHealth(this.stats.getMaxHealth());
    }
 
    public Creature copy() {
        String creatureJson = new ObjectMapper().writeValueAsString(this);
        Creature newCreature = new ObjectMapper().readValue(creatureJson, Creature.class);
        return newCreature;
    }
}