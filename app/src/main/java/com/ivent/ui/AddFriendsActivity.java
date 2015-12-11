package com.ivent.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivent.R;
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.FetchEntities;
import com.ivent.entities.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Activity to let user select friend with checkbox and add them
 */
public class AddFriendsActivity extends ActionBarActivity {

    Button doneButton;
    ListView friendListView;
    private View loadingView;

    List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    ArrayList<String> checkedList = null;
    private List<String> name;
    private List<Integer> photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        //UI objects
        friendListView = (ListView) this.findViewById(R.id.friend_list_view);
        doneButton = (Button) this.findViewById(R.id.done_button);
        // loadingView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listfooter,
        //        null);
        //friendListView.addFooterView(loadingView);

        // new GetUsersAsyncTask().execute();

        //read values
        name = new ArrayList<>();
        name.add("Shan");
        name.add("Zack");
        photo = new ArrayList<>();
        photo.add(R.drawable.shan);
        photo.add(R.drawable.zhengyang);
        showCheckBoxListView();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendsActivity.this.finish();
            }
        });

    }

    //set up check box list view
    public void showCheckBoxListView() {

        for (int i = 0; i < name.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("item_tv", name.get(i));
            map.put("item_icon", photo.get(i));
            map.put("item_cb", false);
            list.add(map);

            MyAdapter adapter = new MyAdapter(this, list, R.layout.add_friend,
                    new String[]{"item_icon", "item_tv", "item_cb"}, new int[]{R.id.photo_image_view,
                    R.id.message_text_view, R.id.check_box});
            friendListView.setAdapter(adapter);
            checkedList = new ArrayList<>();

            friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view,
                                        int position, long arg3) {
                    HolderView holder = (HolderView) view.getTag();
                    holder.checkBox.toggle(); //change checkbox status
                    MyAdapter.isSelected.put(position, holder.checkBox.isChecked()); //
                    if (holder.checkBox.isChecked() == true) {
                        checkedList.add(name.get(position));
                    } else {
                        checkedList.remove(name.get(position));
                    }
                }

            });
        }
    }

    //self defined adapter to show add friend list
    public static class MyAdapter extends BaseAdapter {
        public static HashMap<Integer, Boolean> isSelected;
        private LayoutInflater inflater = null;
        private List<HashMap<String, Object>> list = null;
        private String keyString[] = null;
        private int idValue[] = null;

        public MyAdapter(Context context, List<HashMap<String, Object>> list,
                         int resource, String[] from, int[] to) {
            this.list = list;
            keyString = new String[from.length];
            idValue = new int[to.length];
            System.arraycopy(from, 0, keyString, 0, from.length);
            System.arraycopy(to, 0, idValue, 0, to.length);
            inflater = LayoutInflater.from(context);
            init();
        }

        // initialize check box as false
        public void init() {
            isSelected = new HashMap<>();
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
            holder.imageView = (ImageView) view.findViewById(R.id.photo_image_view);
            holder.textView = (TextView) view.findViewById(R.id.message_text_view);
            holder.checkBox = (CheckBox) view.findViewById(R.id.check_box);
            view.setTag(holder);

            HashMap<String, Object> map = list.get(position);
            if (map != null) {
                //holder.imageView.setImageURI(Uri.parse((String)map.get(keyString[0])));
                holder.imageView.setImageResource((int) map.get(keyString[0]));
                holder.textView.setText((String) map.get(keyString[1]));
            }
            holder.checkBox.setChecked(isSelected.get(position));
            return view;
        }

    }

    //Class of three componets to show an item of adding friend
    public static class HolderView {
        public ImageView imageView = null;
        public TextView textView = null;
        public CheckBox checkBox = null;
    }

    //Asynctask to get all users from database
    public class GetUsersAsyncTask extends AsyncTask<String, Integer, List<User>> {

        @Override
        protected void onPostExecute(List<User> result) {
            super.onPostExecute(result);
            name = new ArrayList<>();
            photo = new ArrayList<>();

            for (User user : result) {
                name.add(user.getName());
                String photo = user.getPhoto();
                if (photo == null) {
                    photo = "android.resource://" + getPackageName() + "/" + R.mipmap.default_user;
                }
                //photo.add(photo);
            }
            showCheckBoxListView();
            dataArrived(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<User> doInBackground(String... arg0) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            boolean networkStatus = sharedPref.getBoolean(getString(R.string.network_status), false);

            FetchEntities fetchEntities = new BuildEntities(getApplicationContext(), networkStatus);
            return fetchEntities.getAllUsers();
        }

    }

    Handler handler = new Handler() {
        public void handleMessage(Message paramMessage) {
            if (paramMessage.what == 1) {
                loadingView.setVisibility(View.GONE);
            } else if (paramMessage.what == 2) {
                friendListView.removeFooterView(loadingView);
            }
        }
    };

    public void dataArrived(boolean isEnd) {

        Message localMessage = new Message();
        if (!isEnd) {
            localMessage.what = 1;
        } else {
            localMessage.what = 2;
        }
        this.handler.sendMessage(localMessage);
    }


}
