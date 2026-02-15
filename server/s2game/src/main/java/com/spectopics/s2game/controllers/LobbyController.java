package com.spectopics.s2game.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;

import com.spectopics.dto.LobbyingDto;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;


@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GetMapping("/join/{lobbyId}/{playerName}")
    public ResponseEntity<?> joinLobby(@PathVariable String lobbyId, @PathVariable String playerName) {
        LobbyState lobby = LobbyState.GetLobby(lobbyId);
        if (lobby == null) {
            System.err.println("Failed bc lobby not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lobby not found");
        }

        Player player = LobbyState.MakePlayer(playerName);
        lobby.AddPlayer(player);
        LobbyingDto dto = new LobbyingDto(lobby, player);
        String json = ow.writeValueAsString(dto);
        System.out.println("Joined Lobby State:" + json);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/create/{lobbyId}/{playerName}")
    public ResponseEntity<?> createLobby(@PathVariable String lobbyId, @PathVariable String playerName) {

        Player player = LobbyState.MakePlayer(playerName);
        LobbyState lobby = LobbyState.AddNew(lobbyId, player);
        if (lobby == null) {
            System.err.println("Failed bc id in use");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id in use");
        }

        LobbyingDto dto = new LobbyingDto(lobby, player);
        String json = ow.writeValueAsString(dto);
        System.out.println("Created Lobby State:" + json);
        return ResponseEntity.ok(json);
    }   
}
