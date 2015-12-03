package com.ivent.entities.adapter;

//interface of creating entities
public interface CreateEntities {
    public boolean createUser(String userName, String password, String uri);

    public void createCategory(String categoryName);

    public void createEvent(String eventName, String categoryName, String location,
                            String eventTime, String description, String imageLink);

    public void createPost(String eventName, String postText, String userName);

    public void createChatMessage(String eventName, String chatText);
}
