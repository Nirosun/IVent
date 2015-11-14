package com.ivent.entities.model;

public class User {
    // user id
    private long id;

    // Facebook id for the user
    private long fbId;

    // name of user
    private String name;

    public User() {

    }

    public User(long id, long fbId, String name) {
        super();
        this.id = id;
        this.fbId = fbId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFbId() {
        return fbId;
    }

    public void setFbId(long fbId) {
        this.fbId = fbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
