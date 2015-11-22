package com.ivent.entities.model;

import java.sql.Timestamp;

/**
 * This class represents a chat message
 */
public class ChatMessage extends Message {
    // TODO: May add other attributes later

    public ChatMessage(String username, String event_name, String text, Timestamp ts) {
        super(username, event_name, text, ts);
    }
}
