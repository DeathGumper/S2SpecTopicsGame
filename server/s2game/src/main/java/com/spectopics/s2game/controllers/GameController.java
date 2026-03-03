// package com.spectopics.s2game.controllers;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.spectopics.s2game.models.LobbyState;
// import com.spectopics.s2game.models.Player;
// import com.spectopics.s2game.services.LobbyService;
// import com.spectopics.s2game.services.LobbyStageService;
// import com.spectopics.s2game.services.PlayerService;


// @RestController
// @RequestMapping("/lobby")
// public class GameController {
//     @GetMapping("/startGame/{lobbyId}/{playerId}")
//     public ResponseEntity<?> startGame(@PathVariable String lobbyId, @PathVariable String playerId) {
//         Player player = PlayerService.GetPlayerById(playerId);

//         LobbyState lobby = LobbyService.GetLobby(lobbyId);
//         if (lobby == null) {
//             System.err.println("Failed bc no lobby with id: " + lobbyId + " was found");
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no matching id");
//         }

//         if (LobbyStageService.StartGame(lobby, player)) {
//             System.err.println("Game started!");
//             return ResponseEntity.ok("Game Started");
//         }

//         System.err.println("Failed bc either already started or player is not the owner.");
//         return ResponseEntity.ok("Lobby either already started or player is not the owner."); 
//     }

//     @GetMapping("/readyup/{lobbyId}/{playerId}")
//     public ResponseEntity<?> readyUp(@PathVariable String lobbyId, @PathVariable String playerId) {
//         LobbyStageService.ReadyUp(LobbyService.GetLobby(lobbyId), PlayerService.GetPlayerById(playerId));

//         return ResponseEntity.ok("");
//     }
// }
