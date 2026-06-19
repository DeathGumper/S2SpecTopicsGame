package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.models.Creature;

public class CreatureStunnedPayload {
    // Tells the clients that a creature has been stunned.
    // They loose their turn.
    public Creature creature;

    public CreatureStunnedPayload(Creature creature) {
        this.creature = creature;
    }
}
