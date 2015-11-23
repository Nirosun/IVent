package com.ivent.LocalDB;

import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;

import java.util.ArrayList;

/**
 * Created by shuo on 15/11/22.
 */
public interface IDatabaseConnector {
    public void insertUser(String name, String pw);
    public boolean checkUser(String name, String pw);

    public void insertCategory(String name);
    public ArrayList<Category> getCategories();

    public void insertEvent(Event event);
    public ArrayList<Event> getEventOfCategory(String category_name);
    public Event getEvent(String event_name);

    public void insertPost(Post post);
    public ArrayList<Post> getPostsFromEvent(String event_name);

    public void insertChatMessage(ChatMessage message);
    public ArrayList<ChatMessage> getMessages(String event_name);
}
