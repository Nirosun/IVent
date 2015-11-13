package com.ivent.ws.local;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Luciferre on 11/13/15.
 */
public class AudioService implements IAudioService {

    private MediaPlayer player;

    public AudioService(Context context, int id) {
        player = MediaPlayer.create(context, id);
    }

    public void start() {
        player.start();
    }

    public void stop() {
        player.stop();
    }
}
