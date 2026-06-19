package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.Creature;

public class CreatureBurnedPayload {
    // Tells the clients that a creature has been burned, and how much damage it took.
    public Creature creature;
    public float damage;

    public CreatureBurnedPayload(Creature creature, float damage) {
        this.creature = creature;
        this.damage = damage;
    }
}
