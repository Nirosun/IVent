package com.ivent.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.CreateEntities;
import com.ivent.entities.model.Category;
import com.ivent.exception.IVentAppException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class CreateEventActivity extends ActionBarActivity {

    // event time
    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private Spinner dateSpinner;
    private Spinner timeSpinner;

    // event category
    private Spinner categorySpinner;

    // done creating button
    private Button doneButton;

    // add friends button
    private Button addFriendsButton;

    // a button to upload one background image for this event
    private Button addCoverPhotoButton;

    // event name
    private EditText nameEditText;

    // event location
    private EditText locationEditText;

    // event description
    private EditText detailEditText;

    // image view for over photo
    private ImageView imageView;

    private String imageURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

            /* IO objects, get the input values */
        yearSpinner = (Spinner) findViewById(R.id.newevent_year);
        monthSpinner = (Spinner) findViewById(R.id.newevent_month);
        dateSpinner = (Spinner) findViewById(R.id.newevent_date);
        timeSpinner = (Spinner) findViewById(R.id.newevent_time);
        categorySpinner = (Spinner) findViewById(R.id.newevent_category);
        detailEditText = (EditText) findViewById(R.id.edit_detail);
        locationEditText = (EditText) findViewById(R.id.edit_location);
        nameEditText = (EditText) findViewById(R.id.edit_name);
        imageView = (ImageView) findViewById(R.id.newevent_image_view);
        doneButton = (Button) findViewById(R.id.newevent_done_button);
        addFriendsButton = (Button) findViewById(R.id.newevent_add_friends_button);
        addCoverPhotoButton = (Button) findViewById(R.id.newevent_add_cover_photo_button);

        //set up adapters for spinners
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

        ArrayList<String> filterDataFromCategoryList =
                getIntent().getStringArrayListExtra(CategoryListActivity.FILTER_DATA);

        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, filterDataFromCategoryList);

        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(adapterYear);
        monthSpinner.setAdapter(adapterMonth);
        dateSpinner.setAdapter(adapterDate);
        timeSpinner.setAdapter(adapterTime);
        categorySpinner.setAdapter(adapterCategory);

        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFriendsIntent = new Intent(CreateEventActivity.this, AddFriendsActivity.class);
                /* put the other information of events to the add_friend_activity */
                addFriendsIntent.putExtra("eventName", nameEditText.getText().toString());
                addFriendsIntent.putExtra("year", yearSpinner.toString());
                addFriendsIntent.putExtra("month", monthSpinner.toString());
                addFriendsIntent.putExtra("date", dateSpinner.toString());
                addFriendsIntent.putExtra("time", timeSpinner.toString());
                addFriendsIntent.putExtra("location", locationEditText.getText().toString());
                addFriendsIntent.putExtra("detail", detailEditText.getText().toString());
                // FIXME: put image here ?
                startActivity(addFriendsIntent);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String eventName = nameEditText.getText().toString();
                    String location = locationEditText.getText().toString();
                    String details = detailEditText.getText().toString();

                    StringBuilder sb = new StringBuilder();
                    sb.append(yearSpinner.getSelectedItem().toString()).append("-")
                            .append(monthSpinner.getSelectedItem().toString()).append("-")
                            .append(dateSpinner.getSelectedItem().toString()).append(" ")
                            .append(timeSpinner.getSelectedItem().toString()).append(":00.000");
                    String time = sb.toString();

                    String category = categorySpinner.getSelectedItem().toString();

                    if (eventName.equals("") || location.equals("") || details.equals("")) {
                        throw new IVentAppException(IVentAppException.ExceptionEnum.MISSING_INPUT);
                    }

                    // FIXME: Now don't require a cover photo to be added
//                    if (imageURI == null) {
//                        throw new IVentAppException(IVentAppException.ExceptionEnum.MISSING_EVENT_IMAGE);
//                    }

                    new CreateEventAsyncTask().execute(eventName, category, location, time, details, imageURI);

                    Intent intent = new Intent(CreateEventActivity.this, CategoryListActivity.class);
                    startActivity(intent);
                } catch (IVentAppException e) {
                    e.fix(CreateEventActivity.this, e.getErrorNo());
                }
            }
        });

        addCoverPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                imageURI = uri.toString();
                System.out.println("Image uri:" + imageURI);

                ContentResolver cr = this.getContentResolver();
                try {
                    System.out.println(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    if (bitmap == null) {
                        System.out.println("bitmap is null");
                        return;
                    }
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException fe) {
                    fe.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class CreateEventAsyncTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String eventName = params[0];
            String category = params[1];
            String location = params[2];
            String time = params[3];
            String details = params[4];

            CreateEntities creator = new BuildEntities(CreateEventActivity.this);
            creator.createEvent(eventName, category, location, time, details, imageURI);

            return null;
        }
    }
}
