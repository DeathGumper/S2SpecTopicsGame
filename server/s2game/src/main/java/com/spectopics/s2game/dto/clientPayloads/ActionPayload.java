package com.spectopics.s2game.dto.clientPayloads;



public class ActionPayload {
    // All the info the client will need to send in order for the server to call the action should be passed here
    // This needs to match the front end

    // Format: "DamageEnemy-50|HealSelf-20|Stun-30"
    public String action;

    public ActionPayload(String playerId, String action) {
        this.action = action;
    }
}
