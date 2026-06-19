package com.spectopics.s2game.services;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.dto.clientPayloads.ActionPayload;
import com.spectopics.s2game.dto.clientPayloads.CreateLobbyPayload;
import com.spectopics.s2game.dto.clientPayloads.JoinLobbyPayload;
import com.spectopics.s2game.dto.clientPayloads.ReadyUpPayload;
import com.spectopics.s2game.dto.clientPayloads.StartGamePayload;
import com.spectopics.s2game.models.Creature;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;


/*
 * Any commands from the clients go thru this class
 * It calls logic and then returns things to the client if needed.
*/
@Service
public class LobbyCommandService {
    private GameEventService gameEventService;
    private ActionService actionService;

    public LobbyCommandService(GameEventService gameEventService, ActionService actionService) {
        // Get the game event service not from the injection, but from the SocketServiceRouter to avoid circular dependency issues
        this.gameEventService = gameEventService;
        this.actionService = actionService;
    }

    // Refresh called from client.
    public void getCreatureBuyOptions(Boolean reroll, WebSocketSession session) {
        // Get player and lobby
        Player player = PlayerService.GetPlayerBySession(session);
        LobbyState lobby = LobbyService.GetLobbyByPlayerId(player.getId());

        // Bad call.
        if (lobby == null) {
            System.out.println("Error: Player " + player.getName() + " is not in a lobby");
            return;
        }

        // Get the buy options and then remove the money if they rerolled
        Creature[] buyOptions = CreatureService.GetRandomCreatureOptions(5);

        if (reroll == true) {
            if (player.getMoney() < 30) {
                System.out.println("Player is broke hahahaha!");
                return;
            }
            player.setMoney(player.getMoney() - 30);
        }

        // Return.
        gameEventService.sendCreatureBuyOptions(session, buyOptions);
        gameEventService.sendLobbyStateToClients(lobby);
    }

    // Create lobby.
    public void handleCreateLobby(CreateLobbyPayload payload, WebSocketSession session) throws Exception {

        // Owner!
        Player owner = PlayerService.MakePlayer(payload.playerName, session);
        LobbyState lobby = LobbyService.AddNew(payload.lobbyId, owner);
        
        // If the lobby already exists w the lobby id requested, then say no no no, bad client, try again
        if (lobby == null)
            throw new Exception("A lobby with id " + payload.lobbyId + " already exists.");
        

        // send back to client that they have successfully created and joined the lobby
        gameEventService.lobbyJoined(lobby, session, owner.getId());
    }

    public void handleJoinLobby(JoinLobbyPayload payload, WebSocketSession session) throws Exception {
        // Find the lobby and then add player to it.
        Player player = PlayerService.MakePlayer(payload.playerName, session);
        LobbyState lobby = LobbyService.GetLobby(payload.lobbyId);

        if (lobby == null) {
            throw new Exception("Lobby not found with id " + payload.lobbyId);
        }

        // Try to add player to lobby, fails if full or game has started.
        if (LobbyService.AddPlayerToLobby(lobby, player)) {
            gameEventService.lobbyJoined(lobby, session, player.getId());
        }
        else {
            throw new Exception("Lobby has either max players or it is not in the lobby stage!");
        }

    }

    public void handleStartGame(StartGamePayload payload, WebSocketSession session) throws Exception {
        // Start the game, if the player is the owner and the lobby is in the lobby stage.
        if (LobbyStageService.StartGame(LobbyService.GetLobby(payload.lobbyId), PlayerService.GetPlayerBySession(session)))
            gameEventService.buyStageStarted(LobbyService.GetLobby(payload.lobbyId));   
        else {
            System.out.println("Failed to start game for lobby with id: " + payload.lobbyId);
        }
    }
    public void handleReadyUp(ReadyUpPayload payload, WebSocketSession session) throws Exception {
        // TODO: check if player is in a lobby before "readying up"
        Player player = PlayerService.GetPlayerBySession(session);
        System.out.println("Player: " + player.getName() + " is ready!");
        player.setReady(true);

        //Returns the lobby state to the client.

        gameEventService.sendLobbyStateToClients(LobbyService.GetLobbyByPlayerId(player.getId()));
    }

    // Check if all battles are done, if they are then we can move to the next stage.
    public void handleBattleEnd(String lobbyId, WebSocketSession session) throws Exception {
        LobbyState lobby = LobbyService.GetLobby(lobbyId);

        if (LobbyStageService.EndBattleStage(lobby)) {
            gameEventService.resultsStageStarted(lobby);
        }
    }

    // buy the creature
    public void buyCreature(Creature creature, WebSocketSession session) {
        // Get player
        Player player = PlayerService.GetPlayerBySession(session);

        // If they have money then buy other wise you too broke bruh hahah
        if (player.getMoney() >= creature.getPrice()) {
            PlayerService.GivePlayerCreature(player, creature);
            player.setMoney(player.getMoney() - creature.getPrice());
            gameEventService.sendCreatureBuyOptions(session, CreatureService.GetRandomCreatureOptions(5));
            System.out.println("Player bought a creature!");
        }
        else {
            System.out.println("Player is broke hahahaha!");
        }

        // Return
        gameEventService.sendLobbyStateToClients(LobbyService.GetLobbyByPlayerId(player.getId()));
    }

    public void handleActionCalled(ActionPayload payload, WebSocketSession session) throws Exception {
        // Send the action to the parser and then returns any changes!
        
        Player player = PlayerService.GetPlayerBySession(session);

        System.out.println("Player " + player.getName() + " has called action: " + payload.action);
        actionService.CallAction(player, payload.action);

        gameEventService.sendLobbyStateToClients(LobbyService.GetLobbyByPlayerId(player.getId()));
    }

    public void handlePlayerDisconnect(WebSocketSession session) throws Exception {
        // Remove the session from the player that disconnected
        Player playerDisconnected = PlayerService.GetPlayerBySession(session);
        if (playerDisconnected == null) return;

        LobbyState lobby = LobbyService.GetLobbyByPlayerId(playerDisconnected.getId());
        PlayerService.RemovePlayer(playerDisconnected);

        // TODO: if a player disconnects then the game should end and the other player should be declared the winner.

        if (lobby != null) {
            gameEventService.sendLobbyStateToClients(lobby);
        }
        
    }
}
