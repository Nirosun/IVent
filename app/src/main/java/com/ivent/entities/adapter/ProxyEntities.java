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
public abstract class ProxyEntities {
    // TODO: DB Connector here

    public void createUser(String userName, String password) {

    }

    public void createCategory(String categoryName) {

    }

    public void createEvent(String eventName, String categoryName, String location,
                            String eventTime, String description, String imageLink) {

    }

    public void createPost(String eventName, String postText, String imageLink) {

    }

    public void createChatMessage(String eventName, String chatText) {

    }

    public User getUser() {
        return null;
    }

    public List<Category> getAllCategories() {
        return null;
    }

    public List<Event> getEventsByCategoryName(String categoryName) {
        return null;
    }

    public Event getEventByName(String eventName) {
        return null;
    }


    public List<Post> getPostsByEventName(String eventName) {
        return null;
    }

    public List<ChatMessage> getChatMessagesByEventName(String eventName) {
        return null;
    }
}
