package com.spectopics.s2game.dto.clientPayloads;



public class ActionPayload {
    // All the info the client will need to send in order for the server to call the action should be passed here
    // This needs to match the front end
    public String lobbyId;
    public String actionId;

    public ActionPayload(String lobbyId, String actionId) {
        this.lobbyId = lobbyId;
        this.actionId = actionId;
    }
}
