package com.ivent.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.com.ivent.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddFriendsActivity extends ActionBarActivity {
    Button doneButton;
    ListView friendListView;
    List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    String[] name = new String[]{"Shan Gao", "Zack Zuo"};
    Object[] icons = new Object[]{R.drawable.shan, R.drawable.zhengyang};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        friendListView = (ListView) this.findViewById(R.id.friend_list_view);
        doneButton = (Button) this.findViewById(R.id.add_friend_done_button);

        showCheckBoxListView();

    }

    public void showCheckBoxListView() {

        for (int i = 0; i < name.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("item_tv", name[i]);
            map.put("item_icon", icons[i]);
            map.put("item_cb", false);
            list.add(map);

            MyAdapter adapter = new MyAdapter(this, list, R.layout.add_friend,
                    new String[]{"item_icon", "item_tv", "item_cb"}, new int[]{R.id.item_icon,
                    R.id.item_tv, R.id.item_cb});
            friendListView.setAdapter(adapter);
        }
    }


    public static class MyAdapter extends BaseAdapter {
        public static HashMap<Integer, Boolean> isSelected;
        private Context context = null;
        private LayoutInflater inflater = null;
        private List<HashMap<String, Object>> list = null;
        private String keyString[] = null;
        private String itemString = null; // 记录每个item中textview的值
        private int idValue[] = null;// id值

        public MyAdapter(Context context, List<HashMap<String, Object>> list,
                         int resource, String[] from, int[] to) {
            this.context = context;
            this.list = list;
            keyString = new String[from.length];
            idValue = new int[to.length];
            System.arraycopy(from, 0, keyString, 0, from.length);
            System.arraycopy(to, 0, idValue, 0, to.length);
            inflater = LayoutInflater.from(context);
            init();
        }

        // 初始化 设置所有checkbox都为未选择
        public void init() {
            isSelected = new HashMap<Integer, Boolean>();
            for (int i = 0; i < list.size(); i++) {
                isSelected.put(i, false);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup arg2) {
            HolderView holder = new HolderView();

            if (view == null) {
                view = inflater.inflate(R.layout.add_friend, null);
            }
            holder.iv = (ImageView) view.findViewById(R.id.item_icon);
            holder.tv = (TextView) view.findViewById(R.id.item_tv);
            holder.cb = (CheckBox) view.findViewById(R.id.item_cb);
            view.setTag(holder);

            HashMap<String, Object> map = list.get(position);
            if (map != null) {

                itemString = (String) map.get(keyString[1]);
                holder.iv.setImageResource((int) map.get(keyString[0]));
                holder.tv.setText(itemString);
            }
            holder.cb.setChecked(isSelected.get(position));
            return view;
        }

    }

    public static class HolderView {
        public ImageView iv = null;
        public TextView tv = null;
        public CheckBox cb = null;
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
