package com.spectopics.s2game.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;

import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;


@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    // Create a player, this will be the way for the client to connect to the server
    @GetMapping("/makePlayer/{playerName}")
    public String makePlayer(@PathVariable String playerName) {
        Player player = LobbyState.MakePlayer(playerName);
        String json = ow.writeValueAsString(player);
        return json;
    }
        

    @GetMapping("/join/{lobbyId}/{playerName}")
    public String joinLobby(@PathVariable String lobbyId, @PathVariable String playerName) {
        LobbyState lobby = LobbyState.GetLobby(lobbyId);
        if (lobby == null) {
            return "Lobby not found";
        }

        lobby.AddPlayer(LobbyState.GetPlayer(playerName));
        String json = ow.writeValueAsString(lobby);
        return json;
    }

    @GetMapping("/create/{lobbyId}/{playerName}")
    public String createLobby(@PathVariable String lobbyId, @PathVariable String playerName) {
        LobbyState lobby = LobbyState.AddNew(lobbyId);
        lobby.AddPlayer(LobbyState.GetPlayer(playerName));
        String json = ow.writeValueAsString(lobby);
        System.out.println("Created Lobby State:" + json);
        return json;
    }   
}
