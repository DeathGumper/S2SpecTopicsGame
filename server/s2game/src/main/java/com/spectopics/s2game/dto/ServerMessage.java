package com.spectopics.s2game.dto;

public class ServerMessage {
    public String type;
    public Object payload;

    public ServerMessage(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
