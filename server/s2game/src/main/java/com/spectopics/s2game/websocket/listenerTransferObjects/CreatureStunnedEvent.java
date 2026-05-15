package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.models.Creature;
import com.spectopics.s2game.models.Player;

public class CreatureStunnedEvent {
    public Player player;
    public Creature creature;

    public CreatureStunnedEvent(Player player, Creature creature) {
        this.player = player;
        this.creature = creature;
    }
}
