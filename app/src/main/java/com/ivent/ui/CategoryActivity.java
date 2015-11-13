package com.ivent.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ivent.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoryActivity extends ActionBarActivity {

    /*
    *   Variables may needed in implementation
    * */

    private List<Map<String, Object>> events; // a list that contains the events of a category

    private ListView listView;

    private ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();

    /*
    *   End of possible variables
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //UI objects
        listView = (ListView) findViewById(R.id.listView);

        //example to show category list
        HashMap<String, String> tmp = new HashMap<>();
        tmp = new HashMap<String, String>();
        tmp.put("event", "Halloween Party");
        eventList.add(tmp);//modify this
        tmp = new HashMap<String, String>();
        tmp.put("event", "Spring Festival Party");
        eventList.add(tmp);
        tmp = new HashMap<String, String>();
        tmp.put("event", "Movie night");
        eventList.add(tmp);
        SimpleAdapter sa = new SimpleAdapter(this, eventList, android.R.layout.simple_list_item_2,
                new String[]{"event"}, new int[]{android.R.id.text2});
        listView.setAdapter(sa);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
