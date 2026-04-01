package com.spectopics.s2game.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spectopics.s2game.enums.StageState;
import com.spectopics.s2game.services.GameUpdaterService;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class LobbyState {
    private String name;
    private String id;

    private List<Player> players;
    private List<Battle> battles;

    private StageState stage;
    private float stageTimer;
    public LobbySettings lobbySettings;
    @JsonIgnore
    private GameUpdaterService gameUpdater;

    public LobbyState(String id, Player owner) {
        this.lobbySettings = new LobbySettings();
        this.name = owner.getName() + "'s lobby";
        this.id = id;
        this.players = new ArrayList<Player>();

        AddPlayer(owner);
        owner.setOwner(true);

        this.stageTimer = 0;
        this.stage = StageState.LOBBY; // In the lobby menu, displaying players in the lobby, not yet into buy phase

        this.gameUpdater = new GameUpdaterService();
        this.gameUpdater.StartUpdating(this);

    }

    public void AddPlayer(Player player) {
        this.players.add(player);
    }

    public void RemovePlayer(Player player) {
        this.players.remove(player);
    }

}