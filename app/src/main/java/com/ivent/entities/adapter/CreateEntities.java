package com.ivent.entities.adapter;

/**
 * Interface of creating entities
 */
public interface CreateEntities {
    /**
     * Create a User object
     * @param userName
     * @param password
     * @param uri
     * @return
     */
    public boolean createUser(String userName, String password, String uri);

    /**
     * Create a Category object
     * @param categoryName
     */
    public void createCategory(String categoryName);

    /**
     * Create an Event object
     * @param eventName
     * @param categoryName
     * @param location
     * @param eventTime
     * @param description
     * @param imageLink
     */
    public void createEvent(String eventName, String categoryName, String location,
                            String eventTime, String description, String imageLink);

    /**
     * Create a Post object
     * @param eventName
     * @param postText
     * @param userName
     */
    public void createPost(String eventName, String postText, String userName);

    /**
     * Create a ChatMessage object
     * @param eventName
     * @param chatText
     */
    public void createChatMessage(String eventName, String chatText);
}
