package com.spectopics.s2game.models;
 
 
import java.util.Map;

import tools.jackson.databind.ObjectMapper;

import lombok.Data;
 
@Data
public class Creature {
    private String name;
    private Stats stats;
    private int price;
    private Map<String, String> abilities;
    private String[] effects;

    public boolean checkStun() {
        // Determines if the creature is stunned. Takes a random chance based on the creature's stun stat, 
        // and if the creature is stunned, resets the stun stat to 0 and returns true. Otherwise, returns false.
        // When stunned their turn is lost!
        float stunChance = (float) (Math.random() * 100);
        if (stunChance < this.stats.getStun()) {
            System.out.println(this.name + " is stunned!");
            this.stats.setStun(0);
            return true;
        }
        return false;
    }

    public float takeBurn() {
        // Takes the burn damage if the creature is on fire, and then reduce the burn stack.
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
        // Multiplies all incomming damage! 1 + (poison stacks / 10). 
        // So 10 stacks of poison would double all damage taken, 20 stacks would triple it, etc.
        if (this.stats.getPoison() > 0) {
            return 1 + (this.stats.getPoison() / 10);
        }
        return 1;
    }

    public void resetStatusEffects() {
        // Full reset of all status effects, used at the end of the battle.
        this.stats.setBurn(0);
        this.stats.setPoison(0);
        this.stats.setStun(0);
    }
 
    public void resetHealth() {
        // Resets health to max, used at the end of the battle.
        this.stats.SetHealth(this.stats.getMaxHealth());
    }
 
    public Creature copy() {
        // Quick copy, used by metadataservice.
        String creatureJson = new ObjectMapper().writeValueAsString(this);
        Creature newCreature = new ObjectMapper().readValue(creatureJson, Creature.class);
        return newCreature;
    }
}