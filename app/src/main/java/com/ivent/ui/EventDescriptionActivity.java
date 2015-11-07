package com.ivent.ui;

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

import com.com.ivent.ui.R;

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
        eventListView = (ListView) findViewById(R.id.event_list_view);
        descriptionTextView = (TextView) findViewById(R.id.event_description_text_view);
        chatButton = (Button) findViewById(R.id.event_chat_button);
        postButton = (Button) findViewById(R.id.event_post_button);
        eventImageView = (ImageView) findViewById(R.id.event_image);

        //read values
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //Example to show event information list
        ArrayList<String> list = new ArrayList<String>();
        list.add("Halloween Party");
        list.add("Oct 31, 19:00");
        list.add("Cohon University Center");
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, list);
        eventListView.setAdapter(myArrayAdapter);

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
}
