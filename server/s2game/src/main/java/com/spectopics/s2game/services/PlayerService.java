package com.spectopics.s2game.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

public class PlayerService {
    
    private static List<Player> allPlayers = new ArrayList<Player>();

    public static Player MakePlayer(String name, WebSocketSession session) {
        Player player = new Player(name, session);
        allPlayers.add(player);
        return player;
    }

    

    public static void RemovePlayer(Player player) {
        allPlayers.remove(player);

        // go thru each lobby and check if the player exists
        // if so then remove
        for (LobbyState lobby: LobbyService.GetAllLobbies()) {
            if (lobby.getPlayers().contains(player)) {
                lobby.RemovePlayer(player);
            }
        }
        // this is inefficient
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

    public static Player GetPlayerBySession(WebSocketSession session) {
        for (Player player : allPlayers) {
            if (player.getId().equals(session.getId())) {
                return player;
            }
        }
        return null;
    }
}
