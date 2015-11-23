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
    private String categoryName;

    // event location
    private String location;

    // event description
    private String description;

    // link to the cover image of the event
    private String imageLink;

    public Event() {

    }

    public Event(String name, Timestamp eventTime, String location,
                 String description, String categoryName, String imageLink) {
        super();
        this.name = name;
        this.eventTime = eventTime;
        this.location = location;
        this.description = description;
        this.imageLink = imageLink;
        this.categoryName = categoryName;
    }

    public void eventPrint(){
        System.out.println( this.name + " " + this.eventTime + " " + this.categoryName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryName(String name) {this.categoryName = name;}

    public String getCategoryName() { return this.categoryName;}

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
