package com.ivent.entities.adapter;

import android.content.Context;

import com.ivent.dblayout.DatabaseConnector;
import com.ivent.dblayout.IDatabaseConnector;
import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by zzuo on 11/22/15.
 */
public abstract class ProxyEntities {
    // Database connector
    IDatabaseConnector dbConn;

    public ProxyEntities(Context context) {
        dbConn = new DatabaseConnector(context);
    }

    public void createUser(String userName, String password, String uri) {
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setPhoto(uri);

        if (!dbConn.checkUser(user))
            dbConn.insertUser(user);
//        else
//            throw new IVentException(IVentException.ExceptionEnum.UserExist);
    }

    public void createCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);

        dbConn.insertCategory(category);
    }

    public void createEvent(String eventName, String categoryName, String location,
                            String eventTime, String description, String imageLink) {
        Event event = new Event();
        event.setName(eventName);
        event.setCategoryName(categoryName);
        event.setLocation(location);
        event.setDescription(description);
        event.setImageLink(imageLink);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date;

        try {
            date = sdf.parse(eventTime);
            event.setEventTime(new Timestamp(date.getTime()));

            dbConn.insertEvent(event);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void createPost(String eventName, String postText, String imageLink) {
        Post post = new Post();
        post.setEventName(eventName);
        post.setText(postText);
        post.setImageLink(imageLink);

        dbConn.insertPost(post);
    }

    public void createChatMessage(String eventName, String chatText) {
        ChatMessage message = new ChatMessage();

        message.setEventName(eventName);
        message.setText(chatText);

        dbConn.insertChatMessage(message);
    }

    public User getUser(String name){
        return dbConn.getUser(name);
    }
    public List<Category> getAllCategories() {
        return dbConn.getCategories();
    }

    public List<Event> getEventsByCategoryName(String categoryName) {
        return dbConn.getEventOfCategory(categoryName);
    }

    public Event getEventByName(String eventName) {
        return dbConn.getEvent(eventName);
    }

    public List<Post> getPostsByEventName(String eventName) {
        return dbConn.getPostsFromEvent(eventName);
    }

    public List<ChatMessage> getChatMessagesByEventName(String eventName) {
        return dbConn.getMessages(eventName);
    }
}
