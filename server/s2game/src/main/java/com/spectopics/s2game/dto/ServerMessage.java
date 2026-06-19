package com.spectopics.s2game.dto;

public class ServerMessage {
    // holder to send for the frontend to parse.
    public String type;
    public Object payload;

    public ServerMessage(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
