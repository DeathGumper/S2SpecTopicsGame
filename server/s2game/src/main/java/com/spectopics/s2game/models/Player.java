package com.spectopics.s2game.models;

import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Player {
    private String name;
    private String id;
    private Creature[] creatures;
    private int activeCreatureIndex;
    private boolean ready;
    private boolean owner = false;
    @JsonIgnore
    private WebSocketSession session;

    public Player(String name, WebSocketSession session) {
        this.name = name;
        this.id = session.getId();
        this.session = session;
        this.creatures = new Creature[5];
        activeCreatureIndex = 0;
    }

    public boolean NextCreature() {
        return SetActiveCreature(activeCreatureIndex + 1);
    }

    public boolean SetActiveCreature(int index) {
        if (index > creatures.length)
            return false;
        activeCreatureIndex = index;
        System.out.println(index);
        return true;
    }

    public Creature GetActiveCreature() {
        return creatures[activeCreatureIndex];
    }

    public boolean AddCreature(Creature creature) {
        for (int i = 0; i < this.creatures.length; i++) {
            if (this.creatures[i] == null) {
                this.creatures[i] = creature;
                return true;
            }
        }

        return false;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return this.ready;
    }
}
