package com.ivent.ws.LocalDB;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.ContentValues;
import android.database.Cursor;

import com.ivent.entities.model.Category;

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
    private static final String table_user = "users";
    private static final String table_category = "categories";
    private static final String table_posts = "posts";
    private static final String table_chat_messages = "chat_messages";
    private static final String table_events = "events";

    /* user table attributes */
    private static final String user_name = "name";
    private static final String user_password = "password";

    /* categories attributes */
    private static final String category_name = "name";

    /* posts attributes */
    private static final String posts_ts = "timestamp";
    private static final String posts_user_name = "user_name";
    private static final String posts_event_name = "event_name";
    private static final String posts_text = "post_text";


    /* chat_messages attributes */
    private static final String chat_messages_ts = "timestamp";
    private static final String chat_message_user_name = "user_name";
    private static final String chat_message_event_name = "event_name";
    private static final String chat_message_chat_text = "chat_text";

    /* events attributes */
    private static final String event_name = "name";
    private static final String event_category_name = "category_name";
    private static final String event_location = "location";
    private static final String event_description = "description";
    private static final String event_img_link = "image_link";
    private static final String event_time = "event_time";


    public DatabaseConnector(Context context){
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() throws SQLException{
        // create or open a database for reading/writing
        if(database == null )
            database = databaseOpenHelper.getWritableDatabase();
    }

    public void close(){
        // close the database connection
        if (database != null)
            database.close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        // public constructor
        public DatabaseOpenHelper(Context context, String name,
                                  CursorFactory factory, int version){
            super(context, name, factory, version);
        } // end DatabaseOpenHelper constructor

        // creates the contacts table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db){
            // create users table
            String user_createQuery = "CREATE TABLE " + table_user + " (" +
                    user_name + " TEXT PRIMARY KEY," +
                    user_password + " TEXT" +
                    ")";
            db.execSQL(user_createQuery);

            // create categories table
            String categories_createQuery = "CREATE TABLE " + table_category + " (" +
                    category_name + " TEXT PRIMARY KEY" +
                    ")";
            db.execSQL(categories_createQuery);

            // create event table
            String event_createQuery = "CREATE TABLE " + table_events + " (" +
                    event_name + " TEXT PRIMARY KEY," +
                    event_category_name + " TEXT ," +
                    event_description + " TEXT ," +
                    event_location + " TEXT ," +
                    event_img_link + " TEXT ," +
                    event_time + " TEXT , " +
                    "FOREIGN KEY (" + event_category_name  + ")  REFERENCES " + table_user +"("+user_name +")" +
                    ")";
            db.execSQL(event_createQuery);

            // create posts table
            String post_createQuery = "CREATE TABLE " + table_posts + " (" +
                    posts_ts + " TIMESTAMP PRIMARY KEY," +
                    posts_user_name + " TEXT ," +
                    posts_event_name + " TEXT ," +
                    posts_text + " TEXT," +
                    "FOREIGN KEY (" + posts_user_name  + ")  REFERENCES " + table_user +"("+user_name +")," +
                    "FOREIGN KEY (" + posts_event_name  + ")  REFERENCES " + table_events +"("+event_name +")" +
                    ")";
            db.execSQL(post_createQuery);

            // create chat table
            String chat_createQuery = "CREATE TABLE " + table_chat_messages + " (" +
                    chat_messages_ts + " TIMESTAMP PRIMARY KEY," +
                    chat_message_user_name + " TEXT ," +
                    chat_message_event_name + " TEXT ," +
                    chat_message_chat_text + " TEXT ," +
                    "FOREIGN KEY (" + chat_message_user_name  + ") REFERENCES " + table_user +"("+user_name +")," +
                    "FOREIGN KEY (" + chat_message_event_name  + ") REFERENCES " + table_events +"("+event_name +")" +
                    ")";
            db.execSQL(chat_createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion){
        }
    }

    public void insert_user(String name, String pw){
        ContentValues new_record = new ContentValues();
        new_record.put(user_name, name);
        new_record.put(user_password, pw);
        open(); // open the database
        database.insert(table_user, null, new_record);
        return;
    }
    public boolean check_user(String name, String pw){
        open();
        Cursor cursor = database.query(table_user,
                new String[]{user_name, user_password},
                user_name + "=? and " + user_password + "=?",
                new String[]{name,pw},
                null,
                null,
                null );
        if( cursor.getCount() == 0) return false;
        else return true;
    }
    public void insert_category(String name){
        ContentValues new_record = new ContentValues();
        new_record.put(category_name, name);
        open(); // open the database
        database.insert(table_category, null, new_record);
        return;
    }
    public List<Category> get_categories(){
        open();
        Cursor cursor = database.query(table_category,
                new String[]{category_name},
                null,
                null,
                null,
                null,
                null );
        ArrayList<Category> ret = new ArrayList<Category>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            ret.add( new Category(cursor.getString(0)));
        }
        return ret;
    }
}
