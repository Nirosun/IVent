package com.ivent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


public class NewEventActivity extends ActionBarActivity {

    /*
     *   Variables may needed in implementation
    * */

    // event time
    private Spinner year;
    private Spinner month;
    private Spinner date;
    private Spinner time;

    // event category
    private Spinner category;

    // two buttons: refer to the XML file
    private Button doneButton;
    private Button addFriendsButton;

    // event name
    private EditText editName;

    // event location
    private EditText editLoation;

    // event description
    private EditText editDetail;

    // a button to upload one background image for this event
    private Button upload;
    private ImageView uploadImage;

    /*
    *   End of possible variables
    * */


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

            /* get the input values */
         year = (Spinner) findViewById(R.id.newevent_year);
         month = (Spinner) findViewById(R.id.newevent_month);
         date = (Spinner) findViewById(R.id.newevent_date);
         time = (Spinner) findViewById(R.id.newevent_time);
         category = (Spinner) findViewById(R.id.newevent_category);
         editDetail = (EditText) findViewById(R.id.edit_detail);
         editLoation = (EditText) findViewById(R.id.edit_location);
         editName = (EditText) findViewById(R.id.edit_name);
         uploadImage = (ImageView) findViewById(R.id.imageViewUpload);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
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
