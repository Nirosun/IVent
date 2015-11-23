package com.ivent.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivent.R;
import com.ivent.entities.model.DisplayedMessage;

import java.util.List;

/**
 * A self defined adapter to show message list
 */
public class MessagesListAdapter extends BaseAdapter {

    private Context context;
    private List<DisplayedMessage> messagesItems;

    public MessagesListAdapter(Context context, List<DisplayedMessage> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */

        DisplayedMessage m = messagesItems.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Identifying the message owner
        if (messagesItems.get(position).isSelf()) {
            // message belongs to you, so load the right aligned layout
            convertView = mInflater.inflate(R.layout.chat_item_mine,
                    null);
        } else {
            // message belongs to other person, load the left aligned layout
            convertView = mInflater.inflate(R.layout.chat_item_other,
                    null);
        }

        TextView lblFrom = (TextView) convertView.findViewById(R.id.chat_title);
        TextView txtMsg = (TextView) convertView.findViewById(R.id.chat_info);
        ImageView icon = (ImageView) convertView.findViewById(R.id.chat_icon);

        txtMsg.setText(m.getMessage().getText());
        lblFrom.setText(m.getFromName());
        icon.setImageResource(m.getIcon());

        return convertView;
    }
}
