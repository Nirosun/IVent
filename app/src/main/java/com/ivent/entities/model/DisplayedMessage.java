package com.ivent.entities.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * This class represents a message that can be displayed on ui
 */
public class DisplayedMessage {
    // the user's name that creates this message
    private String fromName;

    // the real message
    private Message message;

    // whether this message comes from the user self
    private boolean isSelf;

    // resource id for icon
    private int icon;

    public DisplayedMessage() {
    }

    public DisplayedMessage(String fromName, Message message, int icon, boolean isSelf) {
        this.fromName = fromName;
        this.message = message;
        this.isSelf = isSelf;
        this.icon = icon;
    }

    // FIXME: Just for demoing, create dummy message
    public DisplayedMessage(String fromName, String text, int icon, boolean isSelf) {
        this.fromName = fromName;
        this.message = new ChatMessage(null,null,text, new Timestamp(new Date().getTime()));
        this.isSelf = isSelf;
        this.icon = icon;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

}
