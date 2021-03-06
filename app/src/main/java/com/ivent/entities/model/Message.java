package com.ivent.entities.model;

import java.sql.Timestamp;

/**
 * This class represents a general message. It contains basic information of a message. This
 * class is an abstract class and must be extended by other classes to be able to be instantiated.
 */
public abstract class Message {
    // name of user sending the message
    private String username;

    // related event name
    private String eventName;

    // text of the message
    private String text;

    // timestamp of the message
    private Timestamp ts;

    public Message() {

    }

    public Message(String username, String eventName, String text, Timestamp ts) {
        this.username = username;
        this.eventName = eventName;
        this.text = text;
        this.ts = ts;
    }

    public String getUsername() {return username;}

    public void setUsername(String name) { this.username = name;}

    public String getEventName() {return this.eventName;}

    public void setEventName(String name) { this.eventName = name;}

    public String getText() {
        return text;
    }

    public void setText(String text) { this.text = text; }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
}
