package com.spectopics.s2game.models;

import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spectopics.s2game.enums.BattleState;

import lombok.Data;

// CORE DATA FOR PLAYER!

@Data
public class Player {
    private String name;
    // Tracked by id, never name.
    private String id;

    private int lives;
    private Creature[] creatures;
    private int activeCreatureIndex;
    private boolean ready;
    private boolean owner = false;
    private BattleState battleState;
    private float money;

    // Dont send this to client, not needed.
    @JsonIgnore
    private WebSocketSession session;

    // infite loop if we try to send opponent data, so just send opponent id for reference.
    @JsonIgnore
    private Player opponent;

    @JsonProperty("opponentId")
    public String getOpponentId() {
        return opponent != null ? opponent.getId() : null;
    }

    public Player(String name, WebSocketSession session) {
        this.name = name;

        // session is server only, id is what is sent from clients.
        this.id = session.getId();
        this.session = session;
        this.creatures = new Creature[5];
        activeCreatureIndex = 0;
        this.lives = 3;
        this.ready = false;
    }

    public boolean NextCreature() {
        // Next creature, when a creature dies.
        return SetActiveCreature(activeCreatureIndex + 1);
    }

    public boolean SetActiveCreature(int index) {
        // Stops from going to an index that doesnt exist, or is null (no creature there).
        if (index >= creatures.length)
            return false;
        if (creatures[index] == null)
            return false;
        activeCreatureIndex = index;
        System.out.println(index);
        return true;
    }

    public Creature GetActiveCreature() {
        // Just returns the indexed creature.
        return creatures[activeCreatureIndex];
    }

    public boolean AddCreature(Creature creature) {
        // Adds the creature to the next open slot.
        for (int i = 0; i < this.creatures.length; i++) {
            if (this.creatures[i] == null) {
                this.creatures[i] = creature;
                return true;
            }
        }

        return false;
    }

    public void setReady(boolean ready) {
        // ready up
        
        this.ready = ready;
    }

    public boolean isReady() {
        // return ready status
        return this.ready;
    }
}
