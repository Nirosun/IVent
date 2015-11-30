package com.ivent.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivent.R;

import java.util.ArrayList;

//Activity to show detailed information of an event
public class EventDescriptionActivity extends ActionBarActivity {
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
        eventListView = (ListView) findViewById(R.id.event_description_list_view);
        descriptionTextView = (TextView) findViewById(R.id.event_description_text_view);
        chatButton = (Button) findViewById(R.id.event_chat_button);
        postButton = (Button) findViewById(R.id.event_post_button);
        eventImageView = (ImageView) findViewById(R.id.event_image);

        String eventName = getIntent().getStringExtra("event_name");
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

        // set cover photo
//        ContentResolver cr = this.getContentResolver();
//        try {
//            System.out.println(eventImageLink);
//            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(eventImageLink)));
//            if (bitmap == null) {
//                System.out.println("bitmap is null");
//                return;
//            }
//            eventImageView.setImageBitmap(bitmap);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

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
                startActivity(chatIntent);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(EventDescriptionActivity.this, PostActivity.class);
                startActivity(postIntent);
            }
        });

//        //Example to show event information list
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("Halloween Party");
//        list.add("Oct 31, 19:00");
//        list.add("Cohon University Center");
//        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, list);
//        eventListView.setAdapter(myArrayAdapter);

    }

    private void setDefaultCoverPhoto() {
        String urlString = "android.resource://" + getPackageName() + "/" + R.mipmap.default_event_cover;
        eventImageView.setImageURI(Uri.parse(urlString));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SetupCoverPhotoAsyncTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... params) {
            return null;
        }
    }
}
