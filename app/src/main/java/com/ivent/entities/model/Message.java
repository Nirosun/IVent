package com.ivent.entities.model;

/**
 * This class represents a general message. It contains basic information of a message. This
 * class is an abstract class and must be extended by other classes to be able to be instantiated.
 */
public abstract class Message {
    // messsage id
    private long id;

    // id of user sending the message
    private long userId;

    // related event id
    private long eventId;

    // text of the message
    private String text;

    // timestamp of the message
    private long ts;

    public Message(long id, long userId, long eventId, String text, long ts) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.text = text;
        this.ts = ts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
