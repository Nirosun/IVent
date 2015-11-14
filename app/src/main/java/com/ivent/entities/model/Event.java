package com.ivent.entities.model;

import java.sql.Date;

/**
 * This class represents an event
 */
public class Event {
    // event id
    private long id;

    // event name
    private String name;

    // event start time
    private Date eventTime;

    // event location
    private String location;

    // event description
    private String description;

    // link to the cover image of the event
    private String imageLink;

    public Event() {

    }

    public Event(long id, String name, Date eventTime, String location,
                 String description, String imageLink) {
        super();
        this.id = id;
        this.name = name;
        this.eventTime = eventTime;
        this.location = location;
        this.description = description;
        this.imageLink = imageLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
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
