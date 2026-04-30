package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.Creature;

public class CreatureStunnedPayload {
    public Creature creature;

    public CreatureStunnedPayload(Creature creature) {
        this.creature = creature;
    }
}
