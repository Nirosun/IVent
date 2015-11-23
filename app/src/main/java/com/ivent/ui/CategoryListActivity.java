package com.ivent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ivent.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CategoryListActivity extends ActionBarActivity {

    private static final String TAG = "debug";
    ListView categoryListView;
    private View loadingView;
    SimpleAdapter simpleAdapter;
    private boolean isEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        categoryListView = (ListView) findViewById(R.id.category_list);
        loadingView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listfooter,
                null);

        categoryListView.addFooterView(loadingView);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:
            }
        });


//        simpleAdapter = new SimpleAdapter(getApplicationContext(), list, android.R.layout.simple_list_item_2,
//                new String[]{"title"}, new int[]{android.R.id.text2}) {
//
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView text1 = (TextView) view.findViewById(android.R.id.text2);
//                text1.setTextSize(18);
//                return view;
//            }
//        };
//        categoryListView.setAdapter(simpleAdapter);

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
}
