package com.spectopics.s2game.services;

import java.util.ArrayList;
import java.util.List;

import com.spectopics.s2game.enums.StageState;
// import 
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;


public class LobbyService {

    private static List<LobbyState> lobbies = new ArrayList<LobbyState>();
    // All existing players connected to the game

    public static LobbyState AddNew(String id, Player owner) {
        if (GetLobby(id) != null) {
            return null;
        }
        LobbyState lobby = new LobbyState(id, owner);
        lobbies.add(lobby);

        return lobby;
    }

    public static boolean KillLobby(LobbyState lobby) {
        return lobbies.remove(lobby);
    }

    public static List<LobbyState> GetAllLobbies() {
        return lobbies;
    }

    public static LobbyState GetLobby(String id) {
        for (LobbyState lobby : lobbies) {
            if (lobby.getId().equals(id)) {
                return lobby;
            }
        }
        return null;
    }

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
