package com.spectopics.s2game.models;

import java.util.ArrayList;
import java.util.List;

import com.spectopics.enums.StageState;

import lombok.Data;

@Data
public class LobbyState {
    private String name;
    private String id;
    private Player owner;
    private List<Player> players;
    private StageState gameState; // Maybe make this into an enum (LOBBYING, BUYSTAGE, GAMESTAGE)
    private float stageTimer;
    private LobbySettings lobbySettings;
    private Thread updater;

    public LobbyState(String id, Player owner) {
        this.lobbySettings = new LobbySettings();
        this.owner = owner;
        this.name = owner.getName() + "'s lobby";
        this.id = id;
        this.players = new ArrayList<Player>();
        AddPlayer(owner);
        this.stageTimer = 0;
        this.gameState = StageState.LOBBY; // In the lobby menu, displaying players in the lobby, not yet into buy phase
    }

    public boolean StartGame(Player requestMaker) {
        if (!requestMaker.getId().equals(this.owner.getId())) return false;

        if (this.gameState != StageState.LOBBY) return false;
        
        GoToBuyStage();
        return true;
    }

    private void GoToBuyStage() {
        this.gameState = StageState.BUYSTAGE;
        this.stageTimer = this.lobbySettings.getBuyStageTimer();
    }

    private void GoToGameStage() {
        this.gameState = StageState.GAMESTAGE;
    }

    public void AddPlayer(Player player) {
        this.players.add(player);
    }

    // Called 30 times per second
    public void Update(long nanosSinceLastFrame) {
        double timeSinceLastFrame = nanosSinceLastFrame/1_000_000_000.0;
        // If we are in the GameStage or BuyStage then we subtract the timer.
        if (this.gameState != StageState.LOBBY) this.stageTimer -= timeSinceLastFrame; // Subtract the amount of time since the last frame

        System.out.println(this.stageTimer);
    }
    
    public void StopUpdating() {
        if (this.updater != null && this.updater.isAlive()) {
            this.updater.interrupt(); // Interrupt sleep if any
            try {
                this.updater.join(); // Wait for thread to finish cleanly
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void StartUpdating() {
        final double frameTime = 1_000_000_000.0 / 3.0; // nanoseconds per frame (30 fps)
        this.updater = new Thread(() -> {
            long lastTime = System.nanoTime();
            
            while (true) {
                long now = System.nanoTime();

                long delta = now - lastTime;
                if (delta >= frameTime) {
                    lastTime = now;
                    Update(delta);
                }
            }

        });

        this.updater.setDaemon(true); // optional: allows JVM to exit if this is the only thread running
        this.updater.start();
    }




    // STATIC FUNCTIONS

    private static List<LobbyState> lobbies = new ArrayList<LobbyState>();
    // All existing players connected to the game
    private static List<Player> allPlayers = new ArrayList<Player>();

    public static LobbyState AddNew(String id, Player owner) {
        if (GetLobby(id) != null) {
            return null;
        }
        LobbyState lobby = new LobbyState(id, owner);
        lobbies.add(lobby);

        lobby.StartUpdating();

        return lobby;
    }

    public static Player MakePlayer(String name) {
        Player player = new Player(name);
        allPlayers.add(player);
        return player;
    }
    
    // Gets an player that is connected to the server.
    public static Player GetPlayerById(String id) {
        for (Player player : allPlayers) {
            System.out.println(player.getId() + "| " + id);
            if (player.getId().equals(id)) {
                return player;
            }
        }
        return null;
    }

    public static LobbyState GetLobby(String id) {
        for (LobbyState lobby : lobbies) {
            if (lobby.getId().equals(id)) {
                return lobby;
            }
        }
        return null;
    }
}