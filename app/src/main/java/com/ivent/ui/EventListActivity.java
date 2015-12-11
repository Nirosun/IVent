package com.ivent.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ivent.R;
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.FetchEntities;
import com.ivent.entities.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//To show a list of events under a certain category
public class EventListActivity extends ActionBarActivity {

    private static final String TAG = "EventListActivity";

    private ListView eventsListView;

    private List<Event> events;

    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        //UI objects
        eventsListView = (ListView) findViewById(R.id.events_list_view);

        categoryName = getIntent().getStringExtra(CategoryListActivity.CATEGORY_NAME);

        new GetEventsAsyncTask().execute();

        /* Create a new Intent, next activity will be EventDescription, pass the event info */
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos,
                                    long id) {
                Intent intent = new Intent(EventListActivity.this, EventDescriptionActivity.class);

                Event event = events.get(pos);
                intent.putExtra("event_name", event.getName());
                intent.putExtra("event_time", event.getEventTime().toString());
                intent.putExtra("event_location", event.getLocation());
                intent.putExtra("event_description", event.getDescription());
                intent.putExtra("event_image_link", event.getImageLink());
                startActivity(intent);
            }
        });
    }

    //Asynctask to get events from database and set view
    public class GetEventsAsyncTask extends AsyncTask<String, Integer, List<Event>> {
        @Override
        protected void onPostExecute(List<Event> result) {
            super.onPostExecute(result);
            events = result;
            List<HashMap<String, String>> itemsList = new ArrayList<>();
            for (int i = 0; i < events.size(); i++) {
                HashMap<String, String> item = new HashMap<>();
                item.put("key", events.get(i).getName());
                Log.v(TAG, events.get(i).getName());
                itemsList.add(item);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(EventListActivity.this, itemsList, android.R.layout.simple_list_item_2,
                    new String[]{"key"}, new int[]{android.R.id.text2}) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text2);
                    textView.setTextSize(18);
                    return view;
                }
            };
            eventsListView.setAdapter(simpleAdapter);
        }

        @Override
        protected List<Event> doInBackground(String... arg0) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            boolean networkStatus = sharedPref.getBoolean(getString(R.string.network_status), false);

            FetchEntities fetcher = new BuildEntities(getApplicationContext(), networkStatus);
            return fetcher.getEventsByCategoryName(categoryName);
        }

    }
}
