package com.ivent.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.com.ivent.ui.R;
import com.ivent.entities.model.DisplayedMessage;
import com.ivent.entities.MessagesListAdapter;

import java.util.ArrayList;

//Activity to let users chat with each other
public class ChatActivity extends ActionBarActivity {

    private ListView chatListView;
    private EditText editText;
    private Button chatButton;
    private MessagesListAdapter adapter;
    private ArrayList<DisplayedMessage> listMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        listMessages = new ArrayList<DisplayedMessage>();

        //UI objects
        chatListView = (ListView) findViewById(R.id.chat_list_view);
        editText = (EditText) findViewById(R.id.edit_chat);
        chatButton = (Button) findViewById(R.id.chat_button);

        //read values
        editText.getText();
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMessages.add( new DisplayedMessage("Shan Gao", editText.getText().toString(), R.drawable.shan,false));
                adapter = new MessagesListAdapter(ChatActivity.this, listMessages);
                chatListView.setAdapter(adapter);
            }
        });

        //Example to show chat list
        ArrayList<DisplayedMessage> listMessages = new ArrayList<DisplayedMessage>();
        listMessages.add(new DisplayedMessage("Zack Zuo", "Hello Everybody.", R.drawable.zhengyang, false));
        listMessages.add(new DisplayedMessage("Shan Gao", "Hi Zack. Anyone else here?", R.drawable.shan, false));
        adapter = new MessagesListAdapter(this, listMessages);
        chatListView.setAdapter(adapter);
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
