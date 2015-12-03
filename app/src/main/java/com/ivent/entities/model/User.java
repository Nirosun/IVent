package com.ivent.entities.model;

/**
 * This class represents a user.
 */
public class User {
    // name of user
    private String name;

    // password of user
    private String password;

    private String photo;

    public User() {

    }

    public User(String name, String password, String photo) {
        super();
        this.name = name;
        this.password = password;
        this.photo = photo;
    }

    public User(String name, String photo) {
        super();
        this.name = name;
        this.photo = photo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
