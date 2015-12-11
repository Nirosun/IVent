package com.ivent.ws.remote;

import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * This interface is for methods that deal with connection and communication
 * with the backend server
 */
@SuppressWarnings("unused")
public interface IServerInteraction {
    /**
     * Create a user
     * @param user
     * @return
     */
    public boolean createUserOnServer(User user);

    /**
     * Get user by name
     * @param name
     * @return
     */
    public User getUserFromServer(String name);

    /**
     * Create a category
     * @param category
     * @return
     */
    public boolean createCategoryOnServer(Category category);

    /**
     * Get categories using user id
     * @param userId
     * @return
     */
    public List<Category> getCategoriesFromServer(long userId);

    /**
     * Create an event
     * @param event
     * @return
     */
    public boolean createEventOnServer(Event event);

    /**
     * Get events by category id
     * @param categoryId
     * @return
     */
    public List<Event> getEventsFromServer(long categoryId);

    /**
     * Create a post
     * @param post
     * @return
     */
    public boolean createPostOnServer(Post post);

    /**
     * Get posts by event id
     * @param eventId
     * @return
     */
    public List<Post> getPostsFromServer(long eventId);
}
