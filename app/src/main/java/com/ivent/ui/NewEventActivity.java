package com.ivent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ivent.R;


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
    private EditText editLocation;

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

            /* IO objects, get the input values */
        year = (Spinner) findViewById(R.id.newevent_year);
        month = (Spinner) findViewById(R.id.newevent_month);
        date = (Spinner) findViewById(R.id.newevent_date);
        time = (Spinner) findViewById(R.id.newevent_time);
        category = (Spinner) findViewById(R.id.newevent_category);
        editDetail = (EditText) findViewById(R.id.edit_detail);
        editLocation = (EditText) findViewById(R.id.edit_location);
        editName = (EditText) findViewById(R.id.edit_name);
        uploadImage = (ImageView) findViewById(R.id.imageViewUpload);
        addFriendsButton = (Button) findViewById(R.id.newevent_add_friends_button);

        editDetail.getText();
        editLocation.getText();
        editName.getText();
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_friends = new Intent(NewEventActivity.this, AddFriendsActivity.class);
                /* put the other information of events to the add_friend_activity */
                add_friends.putExtra("eventName", editName.getText().toString());
                add_friends.putExtra("year", year.toString());
                add_friends.putExtra("month", month.toString());
                add_friends.putExtra("date", date.toString());
                add_friends.putExtra("time", time.toString());
                add_friends.putExtra("location", editLocation.getText().toString());
                add_friends.putExtra("detail", editDetail.getText().toString());
                // put image here
                startActivity(add_friends);
            }
        });

        //set up adapter view
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(
                this, R.array.year_array,
                android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(
                this, R.array.month_array,
                android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterDate = ArrayAdapter.createFromResource(
                this, R.array.date_array,
                android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(
                this, R.array.time_array,
                android.R.layout.simple_spinner_item);

        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year.setAdapter(adapterYear);
        month.setAdapter(adapterMonth);
        date.setAdapter(adapterDate);
        time.setAdapter(adapterTime);
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
