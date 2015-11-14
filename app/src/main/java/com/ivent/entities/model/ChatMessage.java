package com.ivent.entities.model;

/**
 * This class represents a chat message
 */
public class ChatMessage extends Message {
    // TODO: May add other attributes later

    public ChatMessage(long id, long userId, long eventId, String text, long ts) {
        super(id, userId, eventId, text, ts);
    }
}
