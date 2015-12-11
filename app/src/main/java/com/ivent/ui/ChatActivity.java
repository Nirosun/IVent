package com.ivent.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ivent.R;
import com.ivent.entities.model.DisplayedMessage;
import com.ivent.ui.adapter.MessagesListAdapter;
import com.ivent.ws.local.AudioService;
import com.ivent.ws.local.IAudioService;

import java.util.ArrayList;

/**
 * Activity to let users chat with each other
 */
public class ChatActivity extends ActionBarActivity {

    private static final String TAG = "ChatActivity";
    private ListView chatListView;
    private EditText editText;
    private Button chatButton;
    private MessagesListAdapter adapter;
    private ArrayList<DisplayedMessage> listMessages;
    private String userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //UI objects
        chatListView = (ListView) findViewById(R.id.chat_list_view);
        editText = (EditText) findViewById(R.id.chat_edit_text);
        chatButton = (Button) findViewById(R.id.chat_button);

//        final String userName = getIntent().getStringExtra("userName");
//        userPhoto = getIntent().getStringExtra("userPhoto");

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String userName = sharedPref.getString(getString(R.string.user_name), "");
        userPhoto = sharedPref.getString(getString(R.string.user_photo_link), "");

        Log.v(TAG, userName);

        listMessages = new ArrayList<>();
        //read values
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPhoto == null){
                    userPhoto = "android.resource://" + getPackageName() + "/" + R.mipmap.default_user;
                }
                listMessages.add(new DisplayedMessage(userName, editText.getText().toString(), userPhoto, false));
                adapter = new MessagesListAdapter(ChatActivity.this, listMessages);
                chatListView.setAdapter(adapter);
                editText.setText("");

                IAudioService audioService = new AudioService(ChatActivity.this, R.raw.send_message);
                audioService.start();
            }
        });
    }

}
