package com.spectopics.s2game.websocket.listenerTransferObjects;

import org.springframework.web.socket.WebSocketSession;

import com.spectopics.s2game.models.Creature;

public class GetCreatureBuyOptionsEvent {
    public WebSocketSession session;
    public Creature[] options;

    public GetCreatureBuyOptionsEvent(WebSocketSession session, Creature[] options) {
        this.session = session;
        this.options = options;
    }
}
