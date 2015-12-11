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

    public boolean createUserOnServer(User user);

    public User getUserFromServer(String name);

    public boolean createCategoryOnServer(Category category);

    public List<Category> getCategoriesFromServer(long userId);

    public boolean createEventOnServer(Event event);

    public List<Event> getEventsFromServer(long categoryId);

    public boolean createPostOnServer(Post post);

    public List<Post> getPostsFromServer(long eventId);
}
