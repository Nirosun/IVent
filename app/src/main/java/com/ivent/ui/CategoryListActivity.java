package com.ivent.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ivent.R;
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.CreateEntities;
import com.ivent.entities.adapter.FetchEntities;
import com.ivent.entities.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//To show a list of categories
public class CategoryListActivity extends ActionBarActivity {

    private static final String TAG = "CategoryListActivity";
    private static final String NEW_EVENT = "New Event";
    private static final String NEW_CATEGORY = "New Category";

    protected static final String CATEGORY_NAME = "Category Name";
    protected static final String FILTER_DATA = "Filter Data";

    private ListView categoryListView;
    private View loadingView;
    private SimpleAdapter simpleAdapter;
    private List<Category> categoryList;

    private MenuItem newCategoryMenuItem;
    private MenuItem newEventMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        //UI Objects
        categoryListView = (ListView) findViewById(R.id.category_list);
        loadingView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listfooter,
                null);
        categoryListView.addFooterView(loadingView);

        final String userName = getIntent().getStringExtra("name");
        final String userPhoto = getIntent().getStringExtra("photo");

        new GetCategoryAsyncTask().execute();

        //To start event list activity
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategoryListActivity.this, EventListActivity.class);
                intent.putExtra(CATEGORY_NAME, categoryList.get((int) id).getName());
                intent.putExtra("userName", userName);
                intent.putExtra("userPhoto", userPhoto);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu.size() == 0) {
            newCategoryMenuItem = menu.add(NEW_CATEGORY);
            newEventMenuItem = menu.add(NEW_EVENT);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //start create new event activity
        if (item.equals(newEventMenuItem)) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < categoryList.size(); i++) {
                list.add(categoryList.get(i).getName());
            }

            Intent intent = new Intent(CategoryListActivity.this, CreateEventActivity.class);
            intent.putStringArrayListExtra(FILTER_DATA, list);
            startActivity(intent);

        }
        // new category dialog
        else if (item.equals(newCategoryMenuItem)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(CategoryListActivity.this);
            dialog.setTitle(R.string.edit_category);

            LayoutInflater inflater = CategoryListActivity.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.create_category_layout, null);
            dialog.setView(dialogView);

            final EditText editText = (EditText) dialogView.findViewById(R.id.category_name);

            dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // create new category
                    String newCategoryName = editText.getText().toString();
                    new CreateCategoryAsyncTask().execute(newCategoryName);
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }
        return true;
    }

    //Asynctask to get category list from database and set view
    public class GetCategoryAsyncTask extends AsyncTask<String, Integer, List<Category>> {

        @Override
        protected void onPostExecute(List<Category> result) {
            super.onPostExecute(result);
            categoryList = result;
            List<HashMap<String, String>> list = new ArrayList<>();
            for (int i = 0; i < categoryList.size(); i++) {
                HashMap<String, String> category = new HashMap<>();
                category.put("title", categoryList.get(i).getName());
                Log.v(TAG, categoryList.get(i).getName());
                list.add(category);
            }

            simpleAdapter = new SimpleAdapter(CategoryListActivity.this, list, android.R.layout.simple_list_item_2,
                    new String[]{"title"}, new int[]{android.R.id.text2}) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text2);
                    textView.setTextSize(18);
                    return view;
                }
            };
            categoryListView.setAdapter(simpleAdapter);
            dataArrived(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Category> doInBackground(String... arg0) {
            FetchEntities fetchEntities = new BuildEntities(getApplicationContext());
            return fetchEntities.getAllCategories();
        }

    }

    //Handler to get message from activity
    Handler handler = new Handler() {
        public void handleMessage(Message paramMessage) {
            if (paramMessage.what == 1) {
                loadingView.setVisibility(View.GONE);
            } else if (paramMessage.what == 2) {
                categoryListView.removeFooterView(loadingView);
            }
        }
    };

    //Send message to handler according to whether data is arrived
    public void dataArrived(boolean isEnd) {
        Message localMessage = new Message();
        if (!isEnd) {
            localMessage.what = 1;
        } else {
            localMessage.what = 2;
        }
        this.handler.sendMessage(localMessage);
    }

    //Async task to create category in database and set view
    public class CreateCategoryAsyncTask extends
            AsyncTask<String, Integer, List<Category>> {

        @Override
        protected void onPostExecute(List<Category> result) {
            super.onPostExecute(result);
            categoryList = result;

            List<HashMap<String, String>> list = new ArrayList<>();
            for (int i = 0; i < categoryList.size(); i++) {
                HashMap<String, String> category = new HashMap<>();
                category.put("title", categoryList.get(i).getName());
                list.add(category);
            }

            simpleAdapter = new SimpleAdapter(CategoryListActivity.this, list, android.R.layout.simple_list_item_2,
                    new String[]{"title"}, new int[]{android.R.id.text2}) {

                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text2);
                    textView.setTextSize(18);
                    return view;
                }
            };
            categoryListView.setAdapter(simpleAdapter);
            dataArrived(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Category> doInBackground(String... arg0) {
            List<Category> categories;
            CreateEntities createEntities = new BuildEntities(getApplicationContext());
            createEntities.createCategory(arg0[0]);

            categories = categoryList;
            Category category = new Category();
            category.setName(arg0[0]);
            categories.add(category);
            return categories;
        }

    }
}
