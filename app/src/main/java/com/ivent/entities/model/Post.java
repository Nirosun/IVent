package com.ivent.entities.model;

/**
 * This class represents a post. It's a message with an image link
 */
public class Post extends Message {
    // link to the image in the post
	private String imageLink;

	public Post(long id, long userId, long eventId, String postText, long ts, String imageLink) {
		super(id, userId, eventId, postText, ts);
		this.imageLink = imageLink;
	}

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
