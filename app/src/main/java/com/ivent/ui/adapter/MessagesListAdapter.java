package com.ivent.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivent.R;
import com.ivent.entities.model.DisplayedMessage;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * A self defined adapter to show message list
 */
public class MessagesListAdapter extends BaseAdapter {

    private Context context;
    private List<DisplayedMessage> messages;

    public MessagesListAdapter(Context context, List<DisplayedMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DisplayedMessage displayedMessage = messages.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Identifying the message owner
        if (messages.get(position).isSelf()) {
            // message belongs to you, so load the right aligned layout
            convertView = mInflater.inflate(R.layout.chat_item_mine,
                    null);
        } else {
            // message belongs to other person, load the left aligned layout
            convertView = mInflater.inflate(R.layout.chat_item_other,
                    null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.chat_title);
        TextView chatMessage = (TextView) convertView.findViewById(R.id.chat_info);
        ImageView photo = (ImageView) convertView.findViewById(R.id.chat_icon);

        chatMessage.setText(displayedMessage.getChatMessage().getText());
        name.setText(displayedMessage.getName());

        Uri uri = Uri.parse(displayedMessage.getPhoto());
        ContentResolver contentResolver = context.getContentResolver();
        try {
            System.out.println(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
            photo.setImageBitmap(bitmap);
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }

        return convertView;
    }
}
