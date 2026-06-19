package com.spectopics.s2game.dto.serverPayloads;

import com.spectopics.s2game.enums.StatusNames;
import com.spectopics.s2game.models.Creature;

public class CreatureStatusAppliedPayload {
    // Tells the clients that a creature has had a status applied to it, and which status was applied.
    public Creature creature;
    public StatusNames statusName;

    public CreatureStatusAppliedPayload(Creature creature, StatusNames statusName) {
        this.creature = creature;
        this.statusName = statusName;
    }
}