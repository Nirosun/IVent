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

import android.widget.AdapterView;
import android.content.Intent;
import android.view.View;

public class CategoryActivity extends ActionBarActivity {

    /*
    *   Variables may needed in implementation
    * */

    private ListView listView;

    private ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();

    /*
    *   End of possible variables
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        /* test local DB*/
       /*
        DatabaseConnector connector = new DatabaseConnector(this);
        connector.insertUser("shuo", "123");
        System.out.println(connector.checkUser("shuo", "123"));
        System.out.println(connector.checkUser("shuo", "1223"));

        connector.insertCategory("shuo_test1");
        System.out.println( connector.getCategories());
        connector.insertCategory("shuo_test2");
        System.out.println( connector.getCategories());

        System.out.println("testing event");
        Timestamp ts1 = new Timestamp(new Date().getTime());
        Event test_event = new Event("test1",ts1, "a", "b", "shuo_test1", "link");
        connector.insertEvent(test_event);

        ArrayList<Event> get_event = connector.getEventOfCategory("shuo_test1");
        for(int i = 0;i < get_event.size(); i ++){
           get_event.get(i).eventPrint();
        }
        */
        /* end test */

        //UI objects
        listView = (ListView) findViewById(R.id.category_list);

        //example to show category list
        HashMap<String, String> tmp = new HashMap<String, String>();
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

        /* Create a new Intent, next activity will be EventDescription, pass the eventName */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos,
                                    long id) {
                Intent intent = new Intent(CategoryActivity.this, EventDescriptionActivity.class);
                intent.putExtra("eventName",  (String)eventList.get( (int)id ).get("event"));
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
                Intent new_event = new Intent(CategoryActivity.this, NewEventActivity.class);
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
}
