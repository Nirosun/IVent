package com.ivent.ws.LocalDB;

import com.ivent.entities.model.Category;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Message;
import com.ivent.entities.model.Post;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by shuo on 15/11/22.
 */
public interface IDatabaseConnector {
    public void insertUser(String name, String pw);
    public boolean checkUser(String name, String pw);

    public void insertCategory(String name);
    public List<Category> getCategories();

    public void insertEvent(Event event);
    public List<Event> getEventOfCategory(String category_name);
    public Event getEvent(String event_name);

    public void insertPost(Post post);
    public List<Post> getPostsFromEvent(String event_name);

    public void insertChatMessage(Message message);
    public List<Message> getMessages(String event_name);
}
