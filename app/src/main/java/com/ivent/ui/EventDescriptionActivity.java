package com.ivent.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivent.R;

import java.util.ArrayList;

/**
 * Activity to show detailed information of an event
 */
public class EventDescriptionActivity extends ActionBarActivity {

    private static final String TAG = "EventDescription";
    private TextView descriptionTextView;
    private Button chatButton;
    private Button postButton;
    private ImageView eventImageView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        //UI objects
        eventListView = (ListView) findViewById(R.id.detail_list_view);
        descriptionTextView = (TextView) findViewById(R.id.detail_text_view);
        chatButton = (Button) findViewById(R.id.chat_button);
        postButton = (Button) findViewById(R.id.post_button);
        eventImageView = (ImageView) findViewById(R.id.image_view);

        final String userName = getIntent().getStringExtra("userName");
        final String userPhoto = getIntent().getStringExtra("userPhoto");
        final String eventName = getIntent().getStringExtra("event_name");
        String eventTime = getIntent().getStringExtra("event_time");
        String eventLocation = getIntent().getStringExtra("event_location");
        String eventDescription = getIntent().getStringExtra("event_description");
        String eventImageLink = getIntent().getStringExtra("event_image_link");

        // set data for list view
        ArrayList<String> list = new ArrayList<String>();
        list.add(eventName);
        list.add(eventTime.substring(0, eventTime.length() - 2));
        list.add(eventLocation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, list);
        eventListView.setAdapter(adapter);

        if (eventImageLink != null) {
            eventImageView.setImageURI(Uri.parse(eventImageLink));
        } else {
            setDefaultCoverPhoto();
        }

        // set description
        descriptionTextView.setText(eventDescription);

        // register buttons listeners
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(EventDescriptionActivity.this, ChatActivity.class);
                chatIntent.putExtra("userName", userName);
                chatIntent.putExtra("userPhoto", userPhoto);
                startActivity(chatIntent);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(EventDescriptionActivity.this, PostActivity.class);
                postIntent.putExtra("eventName", eventName);
                postIntent.putExtra("userName", userName);
                postIntent.putExtra("userPhoto", userPhoto);
                startActivity(postIntent);
            }
        });

    }

    //Set default cover photo if there is no one
    private void setDefaultCoverPhoto() {
        String urlString = "android.resource://" + getPackageName() + "/" + R.mipmap.default_event_cover;
        eventImageView.setImageURI(Uri.parse(urlString));
    }

}
