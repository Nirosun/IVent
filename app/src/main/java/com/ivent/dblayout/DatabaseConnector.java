package com.ivent.dblayout;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.ContentValues;
import android.database.Cursor;

import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuo on 15/11/22.
 */


public class DatabaseConnector implements IDatabaseConnector {
    private static final String DATABASE_NAME = "iVent_user_local";
    private SQLiteDatabase database = null; // database object
    private DatabaseOpenHelper databaseOpenHelper;

    /* tables */
    private static final String TABLE_USER = "users";
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_POSTS = "posts";
    private static final String TABLE_CHAT_MESSAGES = "chat_messages";
    private static final String TABLE_EVENTS = "events";

    /* user table attributes */
    private static final String USER_NAME = "name";
    private static final String USER_PASSWORD = "password";

    /* categories attributes */
    private static final String CATEGORY_NAME = "name";

    /* posts attributes */
    private static final String POSTS_TS = "timestamp";
    private static final String POSTS_USER_NAME = "user_name";
    private static final String POSTS_EVENT_NAME = "event_name";
    private static final String POSTS_TEXT = "post_text";


    /* chat_messages attributes */
    private static final String CHAT_MESSAGES_TS = "timestamp";
    private static final String CHAT_MESSAGE_USER_NAME = "user_name";
    private static final String CHAT_MESSAGE_EVENT_NAME = "event_name";
    private static final String CHAT_MESSAGE_CHAT_TEXT = "chat_text";

    /* events attributes */
    private static final String EVENT_NAME = "name";
    private static final String EVENT_CATEGORY_NAME = "category_name";
    private static final String EVENT_LOCATION = "location";
    private static final String EVENT_DESCRIPTION = "description";
    private static final String EVENT_IMG_LINK = "image_link";
    private static final String EVENT_TIME = "event_time";


    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() throws SQLException {
        // create or open a database for reading/writing
        if (database == null)
            database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        // close the database connection
        if (database != null)
            database.close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        // public constructor
        public DatabaseOpenHelper(Context context, String name,
                                  CursorFactory factory, int version) {
            super(context, name, factory, version);
        } // end DatabaseOpenHelper constructor

        // creates the contacts table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {
            // create users table
            String user_createQuery = "CREATE TABLE " + TABLE_USER + " (" +
                    USER_NAME + " TEXT PRIMARY KEY," +
                    USER_PASSWORD + " TEXT" +
                    ")";
            db.execSQL(user_createQuery);

            // create categories table
            String categories_createQuery = "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    CATEGORY_NAME + " TEXT PRIMARY KEY" +
                    ")";
            db.execSQL(categories_createQuery);

            // create event table
            String event_createQuery = "CREATE TABLE " + TABLE_EVENTS + " (" +
                    EVENT_NAME + " TEXT PRIMARY KEY," +
                    EVENT_CATEGORY_NAME + " TEXT ," +
                    EVENT_DESCRIPTION + " TEXT ," +
                    EVENT_LOCATION + " TEXT ," +
                    EVENT_IMG_LINK + " TEXT ," +
                    EVENT_TIME + " TEXT , " +
                    "FOREIGN KEY (" + EVENT_CATEGORY_NAME + ")  REFERENCES " + TABLE_USER + "(" + USER_NAME + ")" +
                    ")";
            db.execSQL(event_createQuery);

            // create posts table
            String post_createQuery = "CREATE TABLE " + TABLE_POSTS + " (" +
                    POSTS_TS + " TIMESTAMP PRIMARY KEY," +
                    POSTS_USER_NAME + " TEXT ," +
                    POSTS_EVENT_NAME + " TEXT ," +
                    POSTS_TEXT + " TEXT," +
                    "FOREIGN KEY (" + POSTS_USER_NAME + ")  REFERENCES " + TABLE_USER + "(" + USER_NAME + ")," +
                    "FOREIGN KEY (" + POSTS_EVENT_NAME + ")  REFERENCES " + TABLE_EVENTS + "(" + EVENT_NAME + ")" +
                    ")";
            db.execSQL(post_createQuery);

            // create chat table
            String chat_createQuery = "CREATE TABLE " + TABLE_CHAT_MESSAGES + " (" +
                    CHAT_MESSAGES_TS + " TIMESTAMP PRIMARY KEY," +
                    CHAT_MESSAGE_USER_NAME + " TEXT ," +
                    CHAT_MESSAGE_EVENT_NAME + " TEXT ," +
                    CHAT_MESSAGE_CHAT_TEXT + " TEXT ," +
                    "FOREIGN KEY (" + CHAT_MESSAGE_USER_NAME + ") REFERENCES " + TABLE_USER + "(" + USER_NAME + ")," +
                    "FOREIGN KEY (" + CHAT_MESSAGE_EVENT_NAME + ") REFERENCES " + TABLE_EVENTS + "(" + EVENT_NAME + ")" +
                    ")";
            db.execSQL(chat_createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
        }
    }

    @Override
    public void insertUser(User user) {
        ContentValues new_record = new ContentValues();
        new_record.put(USER_NAME, user.getName());
        new_record.put(USER_PASSWORD, user.getPassword());
        open(); // open the database
        database.insert(TABLE_USER, null, new_record);
        return;
    }

    @Override
    public boolean checkUser(User user) {
        open();
        Cursor cursor = database.query(TABLE_USER,
                new String[]{USER_NAME, USER_PASSWORD},
                USER_NAME + "=? and " + USER_PASSWORD + "=?",
                new String[]{user.getName(), user.getPassword()},
                null,
                null,
                null);
        if (cursor.getCount() == 0) return false;
        else return true;
    }

    @Override
    public void insertCategory(Category category) {
        ContentValues new_record = new ContentValues();
        new_record.put(CATEGORY_NAME, category.getName());
        open(); // open the database
        database.insert(TABLE_CATEGORY, null, new_record);
        return;
    }

    @Override
    public List<Category> getCategories() {
        open();
        Cursor cursor = database.query(TABLE_CATEGORY,
                new String[]{CATEGORY_NAME},
                null,
                null,
                null,
                null,
                null);
        List<Category> ret = new ArrayList<Category>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ret.add(new Category(cursor.getString(0)));
        }
        return ret;
    }

    @Override
    public void insertEvent(Event event) {
        ContentValues new_record = new ContentValues();
        new_record.put(EVENT_CATEGORY_NAME, event.getCategoryName());
        new_record.put(EVENT_DESCRIPTION, event.getDescription());
        new_record.put(EVENT_LOCATION, event.getLocation());
        new_record.put(EVENT_NAME, event.getName());

        new_record.put(EVENT_TIME, event.getEventTime().getTime());
        new_record.put(EVENT_IMG_LINK, event.getImageLink());
        open(); // open the database
        database.insert(TABLE_EVENTS, null, new_record);
    }

    @Override
    public List<Event> getEventOfCategory(String category_name) {
        Cursor cursor = database.query(TABLE_EVENTS,
                new String[]{EVENT_NAME, EVENT_TIME, EVENT_LOCATION, EVENT_DESCRIPTION, EVENT_CATEGORY_NAME, EVENT_IMG_LINK},
                EVENT_CATEGORY_NAME + "=? ",
                new String[]{category_name},
                null,
                null,
                null);
        List<Event> ret = new ArrayList<Event>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Timestamp ts = new Timestamp(cursor.getLong(1));
            Event event = new Event(cursor.getString(0), ts, cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
            ret.add(event);
        }
        return ret;
    }

    @Override
    public Event getEvent(String event_name) {
        Cursor cursor = database.query(TABLE_EVENTS,
                new String[]{event_name, EVENT_TIME, EVENT_LOCATION, EVENT_DESCRIPTION, EVENT_CATEGORY_NAME, EVENT_IMG_LINK},
                event_name + "=? ",
                new String[]{event_name},
                null,
                null,
                null);
        ArrayList<Event> ret = new ArrayList<Event>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Timestamp ts = new Timestamp(cursor.getLong(1));
            Event event = new Event(cursor.getString(0), ts, cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
            return event;
        }
        return null;
    }

    @Override
    public void insertPost(Post post) {
        ContentValues new_record = new ContentValues();
        new_record.put(POSTS_EVENT_NAME, post.getEventName());
        new_record.put(POSTS_TEXT, post.getText());
        new_record.put(POSTS_TS, post.getTs().getTime());
        new_record.put(POSTS_USER_NAME, post.getUsername());
        open(); // open the database
        database.insert(TABLE_POSTS, null, new_record);
    }

    @Override
    public List<Post> getPostsFromEvent(String event_name) {
        Cursor cursor = database.query(TABLE_POSTS,
                new String[]{POSTS_USER_NAME, POSTS_EVENT_NAME, POSTS_TEXT, POSTS_TS},
                POSTS_EVENT_NAME + "=? ",
                new String[]{event_name},
                null,
                null,
                null);
        List<Post> ret = new ArrayList<Post>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Timestamp ts = new Timestamp(cursor.getLong(3));
            Post post = new Post(cursor.getString(0), cursor.getString(1), cursor.getString(2), ts, null);
            ret.add(post);
        }
        return ret;
    }

    @Override
    public void insertChatMessage(ChatMessage message) {
        ContentValues new_record = new ContentValues();
        new_record.put(CHAT_MESSAGE_CHAT_TEXT, message.getText());
        new_record.put(CHAT_MESSAGE_EVENT_NAME, message.getEventName());
        new_record.put(CHAT_MESSAGES_TS, message.getTs().getTime());
        new_record.put(CHAT_MESSAGE_USER_NAME, message.getUsername());
        open(); // open the database
        database.insert(TABLE_CHAT_MESSAGES, null, new_record);
        return;
    }

    @Override
    public List<ChatMessage> getMessages(String event_name) {
        Cursor cursor = database.query(TABLE_CHAT_MESSAGES,
                new String[]{CHAT_MESSAGE_USER_NAME, CHAT_MESSAGE_EVENT_NAME, CHAT_MESSAGE_CHAT_TEXT, CHAT_MESSAGES_TS},
                CHAT_MESSAGE_EVENT_NAME + "=? ",
                new String[]{event_name},
                null,
                null,
                null);
        List<ChatMessage> ret = new ArrayList<ChatMessage>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Timestamp ts = new Timestamp(cursor.getLong(3));
            ChatMessage msg = new ChatMessage(cursor.getString(0), cursor.getString(1), cursor.getString(2), ts);
            ret.add(msg);
        }
        return ret;
    }
}
