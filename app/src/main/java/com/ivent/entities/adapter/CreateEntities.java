package com.ivent.entities.adapter;

/**
 * Created by zzuo on 11/22/15.
 */
public interface CreateEntities {
    public void createUser(String userName, String password, String uri);

    public void createCategory(String categoryName);

    public void createEvent(String eventName, String categoryName, String location,
                            String eventTime, String description, String imageLink);

    public void createPost(String eventName, String postText, String imageLink);

    public void createChatMessage(String eventName, String chatText);
}
