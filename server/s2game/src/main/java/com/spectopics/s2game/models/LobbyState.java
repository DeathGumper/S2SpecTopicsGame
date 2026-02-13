package com.spectopics.s2game.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LobbyState {
    private String name;
    private String id;
    private List<Player> players;
    private static List<LobbyState> lobbies = new ArrayList<LobbyState>();
    // All existing players connected to the game
    private static List<Player> allPlayers = new ArrayList<Player>();

    public LobbyState(String id) {
        this.name = id + " lobby";
        this.id = id;
        this.players = new ArrayList<Player>();
    }

    public void AddPlayer(Player player) {
        this.players.add(player);
    }

    public static LobbyState AddNew(String id) {
        if (GetLobby(id) != null) {
            return null;
        }
        LobbyState lobby = new LobbyState(id);
        lobbies.add(lobby);

        return lobby;
    }

    public static Player MakePlayer(String name) {
        Player player = new Player(name);
        return player;
    }

    public static boolean AddPlayer(String lobbyId, Player player) {
        for (Player p : allPlayers) {
            if (p.getName().equals(player.getName())) {
                return false;
            }
        }
        allPlayers.add(player);
        return true;
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
