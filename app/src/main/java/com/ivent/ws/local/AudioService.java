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

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
                player = null;
            }
        });
    }

    //start play audio
    public void start() {
        if (player != null) {
            player.start();
        }
    }

    //stop play audio
    public void stop() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
