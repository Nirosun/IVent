package com.ivent.entities.model;

/**
 * This class represents a category
 */
public class Category {
    // category name
    private String name;

    public Category() {

    }

    public Category(String name) {
        super();
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
