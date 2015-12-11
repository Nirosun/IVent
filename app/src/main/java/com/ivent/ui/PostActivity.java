package com.ivent.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ivent.R;
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.FetchEntities;
import com.ivent.entities.model.Post;
import com.ivent.entities.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Activity to show event post
public class PostActivity extends ActionBarActivity {

    private ListView postListView;
    private Button postButton;
    private SimpleAdapter simpleAdapter;
    private View loadingView;

    private List<Post> posts = new ArrayList<>();
    private static final String TAG = "PostActivity";
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //UI Objects
        postListView = (ListView) findViewById(R.id.post_list_view);
        postButton = (Button) findViewById(R.id.post_button);
        loadingView = LayoutInflater.from(this).inflate(R.layout.listfooter,
                null);
        postListView.addFooterView(loadingView);

        eventName = getIntent().getStringExtra("eventName");

        new getPostAsyncTask().execute(eventName);

        List<Map<String, Object>> postList = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(this, postList, R.layout.post_item,
                new String[]{"title", "info", "icon"},
                new int[]{R.id.name_text_view, R.id.post_text_view, R.id.photo_image_view});
        postListView.setAdapter(simpleAdapter);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createPostIntent = new Intent(PostActivity.this, CreatePostActivity.class);
                createPostIntent.putExtra("eventName", eventName);
                createPostIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(createPostIntent);
            }
        });
    }

    //Handler to get message from activity
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message paramMessage) {
            if (paramMessage.what == 1) {
                loadingView.setVisibility(View.GONE);
            } else if (paramMessage.what == 2) {
                postListView.removeFooterView(loadingView);
            }
        }
    };

    //Send message to handler according to whether data is arrived
    public void dataArrived(boolean isEnd) {

        Message localMessage = new android.os.Message();
        if (!isEnd) {
            localMessage.what = 1;
        } else {
            localMessage.what = 2;
        }

        this.handler.sendMessage(localMessage);
    }

    //AsyncTask to get posts from database
    public class getPostAsyncTask extends AsyncTask<String, Integer, Boolean> {
        Map<String, Object> map = new HashMap<>();

        @Override
        protected void onPostExecute(Boolean result) {
            posts = (List<Post>) map.get("post");
            List<User> users = (List<User>) map.get("user");

            List<HashMap<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < posts.size(); i++) {
                HashMap<String, Object> item = new HashMap<>();
                item.put("title", posts.get(i).getUsername());
                item.put("info", posts.get(i).getText());
                String photo = users.get(i).getPhoto();
                if (photo == null) {
                    photo = "android.resource://" + getPackageName() + "/" + R.mipmap.default_user;
                }
                item.put("icon", photo);
                list.add(item);
            }

            simpleAdapter = new SimpleAdapter(PostActivity.this, list, R.layout.post_item,
                    new String[]{"title", "info", "icon"},
                    new int[]{R.id.name_text_view, R.id.post_text_view, R.id.photo_image_view});
            postListView.setAdapter(simpleAdapter);
            dataArrived(true);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            boolean networkStatus = sharedPref.getBoolean(getString(R.string.network_status), false);

            FetchEntities fetchEntities = new BuildEntities(PostActivity.this, networkStatus);
            List<Post> posts = fetchEntities.getPostsByEventName(eventName);
            map.put("post", posts);
            List<User> users = new ArrayList<>();
            for (Post post : posts) {
                String userName = post.getUsername();
                User user = fetchEntities.getUser(userName);
                users.add(user);
            }
            map.put("user", users);
            return true;
        }

    }

}
