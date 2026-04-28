package com.spectopics.s2game.websocket.listenerTransferObjects;

import com.spectopics.s2game.models.Creature;
import com.spectopics.s2game.models.Player;

public class CreatureBurnedEvent {
    public Player player;
    public Creature creature;
    public float damage;

    public CreatureBurnedEvent(Player player, Creature creature, float damage) {
        this.player = player;
        this.creature = creature;
        this.damage = damage;
    }
}
