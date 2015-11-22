package com.ivent.entities.model;

import java.sql.Timestamp;

/**
 * This class represents an event
 */
public class Event {
    // event name
    private String name;

    // event start time
    private Timestamp eventTime;

    // event category name
    private String category_name;

    // event location
    private String location;

    // event description
    private String description;

    // link to the cover image of the event
    private String imageLink;

    public Event() {

    }

    public Event(long id, String name, Timestamp eventTime, String location,
                 String description, String category_name, String imageLink) {
        super();
        this.name = name;
        this.eventTime = eventTime;
        this.location = location;
        this.description = description;
        this.imageLink = imageLink;
        this.category_name = category_name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory_name(String name) {this.category_name = name;}

    public String getCategory_name() { return this.category_name;}

    public Timestamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(Timestamp eventTime) {
        this.eventTime = eventTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}
