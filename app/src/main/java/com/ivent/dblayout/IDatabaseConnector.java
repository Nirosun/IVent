package com.ivent.dblayout;

import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.util.List;

/**
 * Interface of database connector
 */
public interface IDatabaseConnector {
    /**
     * Get list of users
     * @return list of users
     */
    public List<User> getUserList();

    /**
     * Insert a user into db
     * @param user
     * @return whether succesfully inserted
     */
    public boolean insertUser(User user);

    /**
     * Check if the user already exist in db
     * @param user
     * @return
     */
    public boolean checkUser(User user);

    /**
     * Get user object using name
     * @param name
     * @return
     */
    public User getUser(String name);

    /**
     * Insert a category into db
     * @param category
     */
    public void insertCategory(Category category);

    /**
     * Get all categories related to a user
     * @return
     */
    public List<Category> getCategories();

    /**
     * Insert a event into db
     * @param event
     */
    public void insertEvent(Event event);

    /**
     * Get events belonging to the category
     * @param category_name
     * @return
     */
    public List<Event> getEventsOfCategory(String category_name);

    /**
     * Get event based on its name
     * @param event_name
     * @return
     */
    public Event getEvent(String event_name);

    /**
     * Insert a post into db
     * @param post
     */
    public void insertPost(Post post);

    /**
     * Get posts of a event from db
     * @param event_name
     * @return
     */
    public List<Post> getPostsFromEvent(String event_name);

    /**
     * Insert chat messages into db
     * @param message
     */
    public void insertChatMessage(ChatMessage message);

    /**
     * Get chat messages of an event
     * @param event_name
     * @return
     */
    public List<ChatMessage> getMessages(String event_name);
}
