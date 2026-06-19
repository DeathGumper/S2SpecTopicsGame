package com.spectopics.s2game.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spectopics.s2game.enums.StageState;
import com.spectopics.s2game.services.GameUpdaterService;

import lombok.Data;

// Core data about the lobby!

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class LobbyState {
    private String name;
    private String id;

    private List<Player> players;

    private StageState stage;
    private float stageTimer;
    public LobbySettings lobbySettings;
    
    // Dont send this to clients, cant be parsed easy and not needed.
    @JsonIgnore
    private GameUpdaterService gameUpdater;

    public LobbyState(String id, Player owner) {
        this.lobbySettings = new LobbySettings();
        this.name = owner.getName() + "'s lobby";
        this.id = id;
        this.players = new ArrayList<Player>();

        // Owner is the person who makes the lobby.
        AddPlayer(owner);
        owner.setOwner(true);

        this.stageTimer = 0;
        this.stage = StageState.LOBBY; // In the lobby menu, displaying players in the lobby, not yet into buy phase

        // Game updater to run an updater loop.
        this.gameUpdater = new GameUpdaterService();
        this.gameUpdater.StartUpdating(this);

    }

    public void AddPlayer(Player player) {
        // Add player to lobby.
        this.players.add(player);
    }

    public void RemovePlayer(Player player) {
        // Kick, bye bye.
        this.players.remove(player);
    }

}