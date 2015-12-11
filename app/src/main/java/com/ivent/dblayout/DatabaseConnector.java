package com.ivent.dblayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.ivent.entities.model.Category;
import com.ivent.entities.model.ChatMessage;
import com.ivent.entities.model.Event;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of sqlite database operations
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
    private static final String USER_PHOTO = "photo";

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

    /**
     * Helper class to create tables in db
     */
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
                    USER_PASSWORD + " TEXT," +
                    USER_PHOTO + " TEXT" +
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

    //insert user
    @Override
    public boolean insertUser(User user) {

        ContentValues new_record = new ContentValues();
        new_record.put(USER_NAME, user.getName());
        new_record.put(USER_PASSWORD, user.getPassword());
        new_record.put(USER_PHOTO, user.getPhoto());
        open(); // open the database
        database.insert(TABLE_USER, null, new_record);

        return true;
    }

    //check user whether exist
    @Override
    public boolean checkUser(User user) {
        open();

        Cursor cursor = database.query(TABLE_USER,
                new String[]{USER_NAME},
                USER_NAME + "=?",
                new String[]{user.getName()},
                null,
                null,
                null);

        if (cursor.getCount() == 0) return false;
        else return true;
    }

    //get user upon name
    @Override
    public User getUser(String name) {
        open();

        Cursor cursor = database.query(TABLE_USER,
                new String[]{USER_NAME, USER_PASSWORD, USER_PHOTO},
                USER_NAME + "=?",
                new String[]{name},
                null,
                null,
                null);
        User user = null;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            return user;
        }

        return null;
    }

    //get all users
    @Override
    public List<User> getUserList() {
        open();

        Cursor cursor = database.query(TABLE_USER,
                new String[]{USER_NAME, USER_PHOTO},
                null,
                null,
                null,
                null,
                null);
        List<User> users = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            User user = new User(cursor.getString(0), cursor.getString(1));
            users.add(user);
        }

        return users;
    }

    //insert category
    @Override
    public void insertCategory(Category category) {

        ContentValues newRecord = new ContentValues();
        newRecord.put(CATEGORY_NAME, category.getName());
        open(); // open the database
        database.insert(TABLE_CATEGORY, null, newRecord);

        return;
    }

    //get all categories
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

    //insert event
    @Override
    public void insertEvent(Event event) {

        ContentValues newRecord = new ContentValues();
        newRecord.put(EVENT_CATEGORY_NAME, event.getCategoryName());
        newRecord.put(EVENT_DESCRIPTION, event.getDescription());
        newRecord.put(EVENT_LOCATION, event.getLocation());
        newRecord.put(EVENT_NAME, event.getName());

        newRecord.put(EVENT_TIME, event.getEventTime().getTime());
        newRecord.put(EVENT_IMG_LINK, event.getImageLink());
        open(); // open the database
        database.insert(TABLE_EVENTS, null, newRecord);

    }

    //get all events under certain category
    @Override
    public List<Event> getEventsOfCategory(String categoryName) {
        open();

        Cursor cursor = database.query(TABLE_EVENTS,
                new String[]{EVENT_NAME, EVENT_TIME, EVENT_LOCATION, EVENT_DESCRIPTION, EVENT_CATEGORY_NAME, EVENT_IMG_LINK},
                EVENT_CATEGORY_NAME + "=? ",
                new String[]{categoryName},
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

    //get event upon name
    @Override
    public Event getEvent(String eventName) {
        open();

        Cursor cursor = database.query(TABLE_EVENTS,
                new String[]{eventName, EVENT_TIME, EVENT_LOCATION, EVENT_DESCRIPTION, EVENT_CATEGORY_NAME, EVENT_IMG_LINK},
                eventName + "=? ",
                new String[]{eventName},
                null,
                null,
                null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Timestamp ts = new Timestamp(cursor.getLong(1));
            Event event = new Event(cursor.getString(0), ts, cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
            return event;
        }

        return null;
    }

    //insert post
    @Override
    public void insertPost(Post post) {

        ContentValues newRecord = new ContentValues();
        newRecord.put(POSTS_EVENT_NAME, post.getEventName());
        newRecord.put(POSTS_TEXT, post.getText());
        newRecord.put(POSTS_TS, post.getTs().getTime());
        newRecord.put(POSTS_USER_NAME, post.getUsername());

        open();
        database.insert(TABLE_POSTS, null, newRecord);
    }

    //get posts under certain event
    @Override
    public List<Post> getPostsFromEvent(String eventName) {
        open();

        Cursor cursor = database.query(TABLE_POSTS,
                new String[]{POSTS_USER_NAME, POSTS_EVENT_NAME, POSTS_TEXT, POSTS_TS},
                POSTS_EVENT_NAME + "=? ",
                new String[]{eventName},
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

    //insert chat message
    @Override
    public void insertChatMessage(ChatMessage message) {

        ContentValues newRecord = new ContentValues();
        newRecord.put(CHAT_MESSAGE_CHAT_TEXT, message.getText());
        newRecord.put(CHAT_MESSAGE_EVENT_NAME, message.getEventName());
        newRecord.put(CHAT_MESSAGES_TS, message.getTs().getTime());
        newRecord.put(CHAT_MESSAGE_USER_NAME, message.getUsername());
        open();
        database.insert(TABLE_CHAT_MESSAGES, null, newRecord);

        return;
    }

    //get chat messages under certain event
    @Override
    public List<ChatMessage> getMessages(String eventName) {
        open();

        Cursor cursor = database.query(TABLE_CHAT_MESSAGES,
                new String[]{CHAT_MESSAGE_USER_NAME, CHAT_MESSAGE_EVENT_NAME, CHAT_MESSAGE_CHAT_TEXT, CHAT_MESSAGES_TS},
                CHAT_MESSAGE_EVENT_NAME + "=? ",
                new String[]{eventName},
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
