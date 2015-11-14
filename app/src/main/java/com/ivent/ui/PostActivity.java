package com.ivent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ivent.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Activity to show event post
public class PostActivity extends ActionBarActivity {

    private ListView postListView;
    private Button postButton;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //UI Objects
        postListView = (ListView) findViewById(R.id.post_list_view);
        postButton = (Button) findViewById(R.id.post_button);

        //read values
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_post = new Intent(PostActivity.this, CreatePostActivity.class);
                startActivity(create_post);
            }
        });

        //Example to show post list
        List<Map<String, Object>> postList = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "Shan");
        map.put("info", "Happy Halloween~ The party will be held at Cohon University Center on October 31st. Enjoy it!");
        map.put("icon", R.drawable.shan);
        postList.add(map);


        simpleAdapter = new SimpleAdapter(this, postList, R.layout.post_item,
                new String[]{"title", "info", "icon"},
                new int[]{R.id.post_title, R.id.post_info, R.id.post_icon});
        postListView.setAdapter(simpleAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
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
