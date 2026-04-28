package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.Creature;

public class CreatureBurnedPayload {
    public Creature creature;
    public float damage;

    public CreatureBurnedPayload(Creature creature, float damage) {
        this.creature = creature;
        this.damage = damage;
    }
}
