package com.ivent.entities.adapter;

import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.util.List;

/**
 * Created by zzuo on 11/22/15.
 */
public interface FetchEntities {
    public User getUser();

    public List<Category> getAllCategories();

    public List<Event> getEventsByCategoryName(String categoryName);

    public Event getEventByName(String eventName);

    public List<Post> getPostsByEventName(String eventName);

    public List<ChatMessage> getChatMessagesByEventName(String eventName);
}
