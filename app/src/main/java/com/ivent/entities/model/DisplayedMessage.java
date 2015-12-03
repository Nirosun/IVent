package com.ivent.entities.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * This class represents a chatMessage that can be displayed on ui
 */
public class DisplayedMessage {
    // the user's name that creates this chatMessage
    private String name;

    // the real chatMessage
    private Message chatMessage;

    // whether this chatMessage comes from the user self
    private boolean isSelf;

    // resource id for photo
    private String photo;

    public DisplayedMessage(String name, Message chatMessage, String photo, boolean isSelf) {
        this.name = name;
        this.chatMessage = chatMessage;
        this.isSelf = isSelf;
        this.photo = photo;
    }

    public DisplayedMessage(String name, String text, String photo, boolean isSelf) {
        this.name = name;
        this.chatMessage = new ChatMessage(null,null,text, new Timestamp(new Date().getTime()));
        this.isSelf = isSelf;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(Message chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

}
