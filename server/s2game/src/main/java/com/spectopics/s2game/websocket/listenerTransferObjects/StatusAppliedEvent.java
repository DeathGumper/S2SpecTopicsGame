package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.enums.StatusNames;
import com.spectopics.s2game.models.Creature;
import com.spectopics.s2game.models.Player;

public class StatusAppliedEvent {
    public Player player;
    public Creature creature;
    public StatusNames statusName;

    public StatusAppliedEvent(Player player, Creature creature, StatusNames statusName) {
        this.player = player;
        this.creature = creature;
        this.statusName = statusName;
    }
}
