package com.spectopics.s2game.services;

import java.util.ArrayList;
import java.util.List;

import com.spectopics.s2game.enums.StageState;
// import 
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;


public class LobbyService {
    // All lobbies
    private static List<LobbyState> lobbies = new ArrayList<LobbyState>();

    // Make new lobby.
    public static LobbyState AddNew(String id, Player owner) {
        // Check if lobby with id already exists, if it does then return null, otherwise make a new one and add it to the list.
        if (GetLobby(id) != null) {
            return null;
        }
        LobbyState lobby = new LobbyState(id, owner);
        lobbies.add(lobby);

        // Return.
        return lobby;
    }

    // Kill lobby when ppl are dc or stuff.
    public static void KillLobby(LobbyState lobby) {
        lobbies.remove(lobby);
        
        System.out.println(lobbies);
    }

    // Get the lobby by a player that is in the lobby.
    public static LobbyState GetLobbyByPlayerId(String id) {
        for (LobbyState lobby : lobbies) {
            for (Player p : lobby.getPlayers()) {
                if (p.getId().equals(id)) return lobby;
            }
        }

        return null;
    }

    public static List<LobbyState> GetAllLobbies() {
        return lobbies;
    }

    // Get lobby by id.
    public static LobbyState GetLobby(String id) {
        for (LobbyState lobby : lobbies) {
            if (lobby.getId().equals(id)) {
                return lobby;
            }
        }
        return null;
    }

    // Add player if the lobby isnt full and the game hasn’t started yet.
    public static boolean AddPlayerToLobby(LobbyState lobby, Player player) {
        if (lobby.getPlayers().size() >= lobby.getLobbySettings().getMaxPlayers()) {
            return false;
        }

        if (lobby.getStage() != StageState.LOBBY) {
            return false;
        }

        lobby.AddPlayer(player);
        return true;
    }
}
