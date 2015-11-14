package com.ivent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ivent.R;


//Activity to let user create a post
public class CreatePostActivity extends ActionBarActivity {

    EditText edit_post;
    ImageView imageView;
    Button addImageButton,doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        //UI objects
        edit_post = (EditText) findViewById(R.id.edit_post_textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        addImageButton = (Button) findViewById(R.id.add_image_button);
        doneButton = (Button) findViewById(R.id.done_button);

        //read values
        edit_post.getText();
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_to_post = new Intent(CreatePostActivity.this, PostActivity.class);
                back_to_post.putExtra("title","Shan");
                back_to_post.putExtra("info", edit_post.getText().toString());
                back_to_post.putExtra("icon",R.drawable.shan);
                startActivity(back_to_post);
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
