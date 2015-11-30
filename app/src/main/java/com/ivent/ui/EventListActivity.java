package com.ivent.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ivent.R;
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.FetchEntities;
import com.ivent.entities.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.AdapterView;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class EventListActivity extends ActionBarActivity {
    private static final String TAG = "EventListActivity";

    private ListView eventsListView;

//    private ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();

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

//        //example to show event list
//        HashMap<String, String> tmp = new HashMap<String, String>();
//        tmp = new HashMap<String, String>();
//        tmp.put("event", "Halloween Party");
//        eventList.add(tmp);//modify this
//        tmp = new HashMap<String, String>();
//        tmp.put("event", "Spring Festival Party");
//        eventList.add(tmp);
//        tmp = new HashMap<String, String>();
//        tmp.put("event", "Movie night");
//        eventList.add(tmp);
//        SimpleAdapter sa = new SimpleAdapter(this, eventList, android.R.layout.simple_list_item_2,
//                new String[]{"event"}, new int[]{android.R.id.text2});
//        listView.setAdapter(sa);

        /* Create a new Intent, next activity will be EventDescription, pass the event info */
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos,
                                    long id) {
                Intent intent = new Intent(EventListActivity.this, EventDescriptionActivity.class);

                Event e = events.get(pos);

                intent.putExtra("event_name", e.getName());
                intent.putExtra("event_time", e.getEventTime().toString());
                intent.putExtra("event_location", e.getLocation());
                intent.putExtra("event_description", e.getDescription());
                intent.putExtra("event_image_link", e.getImageLink());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        item.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent new_event = new Intent(EventListActivity.this, CreateEventActivity.class);
                startActivity(new_event);
                return true;
            }
        });
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class GetEventsAsyncTask extends AsyncTask<String, Integer, List<Event>> {
        @Override
        protected void onPostExecute(List<Event> result) {
            super.onPostExecute(result);
            events = result;
            List<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < events.size(); i++) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("key", events.get(i).getName());
                Log.v(TAG, events.get(i).getName());
                itemsList.add(item);
            }
            SimpleAdapter sa = new SimpleAdapter(EventListActivity.this, itemsList, android.R.layout.simple_list_item_2,
                    new String[]{"key"}, new int[]{android.R.id.text2}) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text2);
                    text1.setTextSize(18);
                    text1.setPadding(10, 0, 0, 2);
                    return view;
                }
            };
            eventsListView.setAdapter(sa);
        }

        @Override
        protected List<Event> doInBackground(String... arg0) {
            FetchEntities fetcher = new BuildEntities(getApplicationContext());
            return fetcher.getEventsByCategoryName(categoryName);
        }

    }
}
