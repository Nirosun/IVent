package com.ivent.entities.model;

import java.sql.Timestamp;

/**
 * This class represents a post. It's a message with an image link
 */
public class Post extends Message {
    // link to the image in the post
	private String imageLink;

    public Post() {

    }

	public Post(String username, String eventName, String text, Timestamp ts, String imageLink) {
		super(username, eventName, text, ts);
		this.imageLink = imageLink;
	}

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
