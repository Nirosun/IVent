package com.ivent.dblayout;

import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.util.List;

//interface of database connector
public interface IDatabaseConnector {
    public List<User> getUserList();

    public boolean insertUser(User user);

    public boolean checkUser(User user);

    public User getUser(String name);


    public void insertCategory(Category category);

    public List<Category> getCategories();


    public void insertEvent(Event event);

    public List<Event> getEventsOfCategory(String category_name);

    public Event getEvent(String event_name);


    public void insertPost(Post post);

    public List<Post> getPostsFromEvent(String event_name);


    public void insertChatMessage(ChatMessage message);

    public List<ChatMessage> getMessages(String event_name);
}
