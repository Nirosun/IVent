package com.ivent.ws.local;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * This class is for create an audio service
 */
public class AudioService implements IAudioService {

    private MediaPlayer player;

    public AudioService(Context context, int id) {
        player = MediaPlayer.create(context, id);
    }

    //start play audio
    public void start() {
        player.start();
    }

    //stop play audio
    public void stop() {
        player.stop();
    }
}
