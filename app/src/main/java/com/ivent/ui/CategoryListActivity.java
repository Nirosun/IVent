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
import java.util.Iterator;
import java.util.List;

public class CategoryListActivity extends ActionBarActivity {

    private static final String TAG = "debug";
    private static final String _EVENT = "New Event";
    private static final String _Category = "New Category";

    private ListView categoryListView;
    private View loadingView;
    private SimpleAdapter simpleAdapter;
    private List<Category> filterData;

    private boolean isEnd = false;
    private MenuItem newCategory;
    private MenuItem newEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        categoryListView = (ListView) findViewById(R.id.category_list);
        loadingView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listfooter,
                null);

        categoryListView.addFooterView(loadingView);

        new GetCateAsyncTask().execute();

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String out = "Click" + filterData.get((int) id).getName();
                Log.v(TAG, out);
                Intent intent = new Intent(CategoryListActivity.this, CategoryActivity.class);
                intent.putExtra("categoryName", filterData.get((int) id).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu.size() == 0) {
            newCategory = menu.add(_Category);
            newEvent = menu.add(_EVENT);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.equals(newEvent)) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < filterData.size(); i++) {
                list.add(filterData.get(i).getName());
            }

            Intent intent = new Intent(CategoryListActivity.this, NewEventActivity.class);
            intent.putStringArrayListExtra("filterData", list);
            startActivity(intent);

        } else if (item.equals(newCategory)) {
            AlertDialog.Builder inner = new AlertDialog.Builder(CategoryListActivity.this);
            inner.setTitle(R.string.edit_category);

            LayoutInflater inflater = CategoryListActivity.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.create_category_layout, null);
            inner.setView(dialogView);

            final EditText editText = (EditText) dialogView.findViewById(R.id.category_name);

            inner.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            inner.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // create new category
                    String newName = editText.getText().toString();
                    updateListView(newName);
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = inner.create();
            alertDialog.show();
        }
        return true;
    }

    public class GetCateAsyncTask extends AsyncTask<String, Integer, List<Category>> {

        @Override
        protected void onPostExecute(List<Category> result) {
            super.onPostExecute(result);
            filterData = result;
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < filterData.size(); i++) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("title", filterData.get(i).getName());
                Log.v(TAG, filterData.get(i).getName());
                list.add(item);
            }
            simpleAdapter = new SimpleAdapter(CategoryListActivity.this, list, android.R.layout.simple_list_item_2,
                    new String[]{"title"}, new int[]{android.R.id.text2}) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text2);
                    text1.setTextSize(18);
                    text1.setPadding(10, 0, 0, 2);
                    return view;
                }
            };
            categoryListView.setAdapter(simpleAdapter);
            dataArrived(result, true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Category> doInBackground(String... arg0) {
            List<Category> categories = null;
            FetchEntities fetchEntities = new BuildEntities(getApplicationContext());
            categories = fetchEntities.getAllCategories();
            return categories;
        }

    }

    Handler handler = new Handler() {
        public void handleMessage(Message paramMessage) {
            if (paramMessage.what == 1) {
                loadingView.setVisibility(View.GONE);
            } else if (paramMessage.what == 2) {
                categoryListView.removeFooterView(loadingView);
            }
        }
    };

    public void dataArrived(List list, boolean isEnd) {
        this.isEnd = isEnd;

        Message localMessage = new Message();
        if (!isEnd) {
            localMessage.what = 1;
        } else {
            localMessage.what = 2;
        }
        this.handler.sendMessage(localMessage);
    }


    public void updateListView(String category) {
        new CreateCateAsyncTask().execute(category);

    }

    public class CreateCateAsyncTask extends
            AsyncTask<String, Integer, List<Category>> {

        @Override
        protected void onPostExecute(List<Category> result) {
            super.onPostExecute(result);
            filterData = result;

            List<HashMap<String, String>> list = new ArrayList<>();
            for (int i = 0; i < filterData.size(); i++) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("title", filterData.get(i).getName());
                list.add(item);
            }

            simpleAdapter = new SimpleAdapter(CategoryListActivity.this, list, android.R.layout.simple_list_item_2,
                    new String[]{"title"}, new int[]{android.R.id.text2}) {

                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text2);
                    text1.setTextSize(18);
                    text1.setPadding(10, 0, 0, 2);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text1);
                    return view;

                }
            };
            categoryListView.setAdapter(simpleAdapter);
            dataArrived(result, true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Category> doInBackground(String... arg0) {
            List<Category> categories = null;
            CreateEntities createEntities = new BuildEntities(getApplicationContext());
            createEntities.createCategory(arg0[0]);

            categories = filterData;
            Category category = new Category();
            category.setName(arg0[0]);
            categories.add(category);
            return categories;
        }

    }
}
