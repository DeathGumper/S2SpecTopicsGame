package com.spectopics.s2game.services;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.dto.clientPayloads.ActionPayload;
import com.spectopics.s2game.dto.clientPayloads.CreateLobbyPayload;
import com.spectopics.s2game.dto.clientPayloads.JoinLobbyPayload;
import com.spectopics.s2game.dto.clientPayloads.ReadyUpPayload;
import com.spectopics.s2game.dto.clientPayloads.StartGamePayload;
import com.spectopics.s2game.models.Battle;
import com.spectopics.s2game.models.Creature;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;

@Service
public class LobbyCommandService {
    private GameEventService gameEventService;

    public LobbyCommandService(GameEventService gameEventService) {
        // Get the game event service not from the injection, but from the SocketServiceRouter to avoid circular dependency issues
        this.gameEventService = gameEventService;
    }

    public void handleCreateLobby(CreateLobbyPayload payload, WebSocketSession session) throws Exception {

        Player owner = PlayerService.MakePlayer(payload.playerName, session);
        System.out.println("lobby id: " + payload.lobbyId + " owner: " + owner.getName());
        LobbyState lobby = LobbyService.AddNew(payload.lobbyId, owner);
        
        // If the lobby already exists w the lobby id requested, then say no no no, bad client, try again
        if (lobby == null)
            throw new Exception("A lobby with id " + payload.lobbyId + " already exists.");
        
        gameEventService.lobbyJoined(lobby, session, owner.getId());

        System.out.println("Lobby created with id: " + lobby.getId() + " and owner: " + owner.getName());

        // send back to client that they have successfully created and joined the lobby
    }

    public void handleJoinLobby(JoinLobbyPayload payload, WebSocketSession session) throws Exception {

        Player player = PlayerService.MakePlayer(payload.playerName, session);
        LobbyState lobby = LobbyService.GetLobby(payload.lobbyId);

        if (lobby == null) {
            throw new Exception("Lobby not found with id " + payload.lobbyId);
        }

        if (LobbyService.AddPlayerToLobby(lobby, player)) {

            System.out.println("Player: " + player.getName() + " joined lobby with id: " + lobby.getId());
            gameEventService.lobbyJoined(lobby, session, player.getId());
        }
        else {
            throw new Exception("Lobby has either max players or it is not in the lobby stage!");
        }

    }

    public void handleStartGame(StartGamePayload payload, WebSocketSession session) throws Exception {
        if (LobbyStageService.StartGame(LobbyService.GetLobby(payload.lobbyId), PlayerService.GetPlayerBySession(session)))
            gameEventService.buyStageStarted(LobbyService.GetLobby(payload.lobbyId));   
        else {
            throw new Exception("The lobby is either not at the proper player count or is not the owner.");
        }
    }

    public void handleReadyUp(ReadyUpPayload payload, WebSocketSession session) throws Exception {
        // TODO: check if player is in a lobby before "readying up"
        Player player = PlayerService.GetPlayerBySession(session);
        System.out.println("Player: " + player.getName() + " is ready!");
        player.setReady(true);

        gameEventService.sendLobbyStateToClients(LobbyService.GetLobbyByPlayerId(player.getId()));
    }

    public void handleBattleEnd(String lobbyId, WebSocketSession session) throws Exception {
        LobbyState lobby = LobbyService.GetLobby(lobbyId);

        if (LobbyStageService.EndBattleStage(lobby)) {
            gameEventService.resultsStageStarted(lobby);
        }
    }

    public void buyRandomCreature(WebSocketSession session) {
        Player player = PlayerService.GetPlayerBySession(session);
        Creature creature = CreatureService.GetRandomCreature();
        PlayerService.GivePlayerCreature(player, creature);

        System.out.println("Player " + player.getName() + " has requested to buy a random creature: " + creature);

        gameEventService.sendLobbyStateToClients(LobbyService.GetLobbyByPlayerId(player.getId()));
    }

    public void handleActionCalled(ActionPayload payload, WebSocketSession session) throws Exception {
        Player player = PlayerService.GetPlayerBySession(session);
        LobbyState lobby = LobbyService.GetLobbyByPlayerId(player.getId());
        Battle battle = BattleService.getBattleByPlayerId(lobby.getBattles(), player.getId());

        ActionService.CallAction(battle, payload.action);

        gameEventService.sendLobbyStateToClients(LobbyService.GetLobbyByPlayerId(player.getId()));
    }
}
