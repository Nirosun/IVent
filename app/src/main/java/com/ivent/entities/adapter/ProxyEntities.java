package com.ivent.entities.adapter;

import android.content.Context;

import com.ivent.dblayout.DatabaseConnector;
import com.ivent.dblayout.IDatabaseConnector;
import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;
import com.ivent.ws.remote.IServerInteraction;
import com.ivent.ws.remote.ServerConnector;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//abstract class to implement interfaces through BuildEntities
@SuppressWarnings("unused")
public abstract class ProxyEntities {
    // Database connector
    private IDatabaseConnector dbConn;

    private boolean isNetworkOn;

    public ProxyEntities(Context context, boolean isNetworkOn) {
        this.dbConn = new DatabaseConnector(context);
        this.isNetworkOn = isNetworkOn;
    }

    //create user
    public boolean createUser(String userName, String password, String uri) {
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setPhoto(uri);

        if (isNetworkOn) {
            IServerInteraction serverConn = new ServerConnector();

            boolean success = serverConn.createUserOnServer(user);
            dbConn.insertUser(user);

            return success;
        } else {
            if (!dbConn.checkUser(user)) {
                dbConn.insertUser(user);
                return true;
            }
            return false;
        }
    }

    //create category
    public void createCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);

        dbConn.insertCategory(category);
    }

    //create event
    public void createEvent(String eventName, String categoryName, String location,
                            String eventTime, String description, String imageLink) {
        Event event = new Event();
        event.setName(eventName);
        event.setCategoryName(categoryName);
        event.setLocation(location);
        event.setDescription(description);
        event.setImageLink(imageLink);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date;

        try {
            date = sdf.parse(eventTime);
            event.setEventTime(new Timestamp(date.getTime()));

            dbConn.insertEvent(event);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //create post
    public void createPost(String eventName, String postText, String userName) {
        Post post = new Post();
        post.setEventName(eventName);
        post.setText(postText);
        post.setTs(new Timestamp(System.currentTimeMillis()));
        post.setUsername(userName);

        dbConn.insertPost(post);
    }

    //create chat message
    public void createChatMessage(String eventName, String chatText) {
        ChatMessage message = new ChatMessage();

        message.setEventName(eventName);
        message.setText(chatText);

        dbConn.insertChatMessage(message);
    }

    //get all users
    public List<User> getAllUsers() {
        return dbConn.getUserList();
    }

    //get user by name
    public User getUser(String name) {
        return dbConn.getUser(name);
    }

    //get all categories
    public List<Category> getAllCategories() {
        return dbConn.getCategories();
    }

    //get events under certain category
    public List<Event> getEventsByCategoryName(String categoryName) {
        return dbConn.getEventsOfCategory(categoryName);
    }

    //get event by name
    public Event getEventByName(String eventName) {
        return dbConn.getEvent(eventName);
    }

    //get post under certain event
    public List<Post> getPostsByEventName(String eventName) {
        return dbConn.getPostsFromEvent(eventName);
    }

    //get chat messages under certain event
    public List<ChatMessage> getChatMessagesByEventName(String eventName) {
        return dbConn.getMessages(eventName);
    }
}
