package com.ivent.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ivent.R;
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.CreateEntities;
import com.ivent.exception.IVentAppException;


/**
 * Activity to let user create a post
 */
public class CreatePostActivity extends ActionBarActivity {

    private EditText edit_post;
    private ImageView imageView;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        //UI objects
        edit_post = (EditText) findViewById(R.id.edit_post_textView);
        imageView = (ImageView) findViewById(R.id.image_view);
        doneButton = (Button) findViewById(R.id.done_button);

        Intent intent = getIntent();
        final String eventName = intent.getStringExtra("eventName");

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String userName = sharedPref.getString(getString(R.string.user_name), "");

        System.out.println("user name - " + userName);

        final String userPhoto = sharedPref.getString(getString(R.string.user_photo_link), "");

        //Start post activity and send the new post to it
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String postText = edit_post.getText().toString();
                    if (postText.equals("")) {
                        throw new IVentAppException(IVentAppException.ExceptionEnum.MISSING_INPUT);
                    }

                    new CreatePostAsyncTask().execute(eventName, postText, userName);

//                    Intent intent = new Intent(CreatePostActivity.this, PostActivity.class);
//                    intent.putExtra("eventName", eventName);
//                    startActivity(intent);

                    finish();
                } catch (IVentAppException e) {
                    e.fix(CreatePostActivity.this, e.getErrorNo());
                }
            }
        });
    }

    //Asynctask to create post in database
    public class CreatePostAsyncTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            boolean networkStatus = sharedPref.getBoolean(getString(R.string.network_status), false);

            String eventName = params[0];
            String postText = params[1];
            String userName = params[2];

            CreateEntities createEntities = new BuildEntities(CreatePostActivity.this, networkStatus);
            createEntities.createPost(eventName, postText, userName);
            return null;
        }
    }
}
