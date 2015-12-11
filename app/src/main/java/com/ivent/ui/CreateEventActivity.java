package com.ivent.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ivent.R;
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.CreateEntities;
import com.ivent.exception.IVentAppException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Acitivity to create a new event
 */
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
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        dateSpinner = (Spinner) findViewById(R.id.date_spinner);
        timeSpinner = (Spinner) findViewById(R.id.time_spinner);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        detailEditText = (EditText) findViewById(R.id.detail_edit_text);
        locationEditText = (EditText) findViewById(R.id.location_edit_text);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        imageView = (ImageView) findViewById(R.id.photo_image_view);
        doneButton = (Button) findViewById(R.id.done_button);
        addFriendsButton = (Button) findViewById(R.id.add_friends_button);
        addCoverPhotoButton = (Button) findViewById(R.id.button_add_cover_photo);

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

        //start add friend activity
        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFriendsIntent = new Intent(CreateEventActivity.this, AddFriendsActivity.class);
                startActivity(addFriendsIntent);
            }
        });

        //return to category list activity
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

                    new CreateEventAsyncTask().execute(eventName, category, location, time, details, imageURI);

                    Intent intent = new Intent(CreateEventActivity.this, CategoryListActivity.class);
                    startActivity(intent);
                } catch (IVentAppException e) {
                    e.fix(CreateEventActivity.this, e.getErrorNo());
                }
            }
        });

        //add cover photo
        addCoverPhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

    }

    //set up image view as a result of adding cover photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                imageURI = uri.toString();
                System.out.println("Image uri:" + imageURI);

                ContentResolver contentResolver = this.getContentResolver();
                try {
                    System.out.println(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
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

    //AsyncTask to create events in database
    public class CreateEventAsyncTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... params) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            boolean networkStatus = sharedPref.getBoolean(getString(R.string.network_status), false);

            String eventName = params[0];
            String category = params[1];
            String location = params[2];
            String time = params[3];
            String details = params[4];

            CreateEntities creator = new BuildEntities(CreateEventActivity.this, networkStatus);
            creator.createEvent(eventName, category, location, time, details, imageURI);

            return null;
        }
    }
}
