package com.spectopics.s2game.websocket.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spectopics.s2game.dto.ServerMessage;
import com.spectopics.s2game.dto.clientPayloads.CreateLobbyPayload;
import com.spectopics.s2game.dto.clientPayloads.JoinLobbyPayload;
import com.spectopics.s2game.dto.clientPayloads.ReadyUpPayload;
import com.spectopics.s2game.dto.clientPayloads.StartGamePayload;
import com.spectopics.s2game.models.LobbyState;
import com.spectopics.s2game.models.Player;
import com.spectopics.s2game.services.LobbyCommandService;
import com.spectopics.s2game.services.PlayerService;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;


// Socket-Connection Configuration class
@Component
public class SocketConnectionHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    // In this list all the connections will be stored
    // Then it will be used to broadcast the message
    private final List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    private LobbyCommandService lobbyCommandService;

    // Constructor Injection
    public SocketConnectionHandler(LobbyCommandService lobbyCommandService) {
        this.lobbyCommandService = lobbyCommandService;

        System.out.println("SocketConnectionHandler initialized with LobbyCommandService");
        System.out.println("LobbyCommandService instance: " + lobbyCommandService);
    }

    // This method is executed when client tries to connect
    // to the sockets
    @Override
    public void
    afterConnectionEstablished(WebSocketSession session)
        throws Exception
    {

        super.afterConnectionEstablished(session);
        // Logging the connection ID with Connected Message
        System.out.println(session.getId() + " Connected");

        // Adding the session into the list
        webSocketSessions.add(session);
        
        broadcastToIndividual(session, "WEBSOCKET_CONNECTED", "Successfully connected to WebSocket server");
    }

    // When client disconnect from WebSocket then this
    // method is called
    @Override
    public void afterConnectionClosed(WebSocketSession session,
                          CloseStatus status)throws Exception
    {
        super.afterConnectionClosed(session, status);
        System.out.println(session.getId() + " Disconnected");

        // Removing the connection info from the list
        webSocketSessions.remove(session);
        
        // Remove the session from the player that disconnected
        Player playerDisconnected = PlayerService.GetPlayerBySession(session);
        if (playerDisconnected == null) return;
        PlayerService.RemovePlayer(playerDisconnected);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        JsonNode root = objectMapper.readTree(message.getPayload());

        String type = root.get("type").asString();
        JsonNode payloadNode = root.get("payload");

        // Route normally
        switch (type) {
            case "CREATE_LOBBY":
                lobbyCommandService.handleCreateLobby(
                    objectMapper.treeToValue(payloadNode, CreateLobbyPayload.class), 
                    session
                );
                break;

            case "JOIN_LOBBY":
                lobbyCommandService.handleJoinLobby(
                    objectMapper.treeToValue(payloadNode, JoinLobbyPayload.class), 
                    session
                );
                break;

            case "START_GAME":
                lobbyCommandService.handleStartGame(
                    objectMapper.treeToValue(payloadNode, StartGamePayload.class), 
                    session
                );
                break;

            case "READY_UP":
                lobbyCommandService.handleReadyUp(
                    objectMapper.treeToValue(payloadNode, ReadyUpPayload.class), 
                    session
                );
                break;

            // Temporary
            case "BUY_RANDOM_CREATURE":
                lobbyCommandService.buyRandomCreature(
                    session
                );
                break;
            
            case "END_BATTLES":

                // TODO: get rid of this
                System.out.println("Battle stage is ending!");
                // Ends all battles in that lobbyState
                lobbyCommandService.handleBattleEnd(
                    objectMapper.treeToValue(payloadNode, String.class),
                    session
                );
                break;
        }
    }

    public void broadcastToLobby(LobbyState lobby, String type, Object payload) throws Exception {

        List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
        // Get all sessions in the lobby
        for (var player : lobby.getPlayers()) {
            sessions.add(player.getSession());
        }

        if (sessions == null || sessions.size() == 0) return;

        ObjectMapper mapper = new ObjectMapper();

        ServerMessage message = new ServerMessage(type, payload);
        String json = mapper.writeValueAsString(message);

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(json));
            }
        }
    }

    public void broadcastToIndividual(WebSocketSession session, String type, Object payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ServerMessage message = new ServerMessage(type, payload);
        String json = mapper.writeValueAsString(message);

        if (session.isOpen()) {
            session.sendMessage(new TextMessage(json));
        }
    }
}
