package com.ivent.entities.model;

public class User {
    // name of user
    private String name;

    // password of user
    private String password;

    public User() {

    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {this.password = password;}

    public String getPassword() {return this.password;}
}
