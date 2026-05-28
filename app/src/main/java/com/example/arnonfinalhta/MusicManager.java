package com.example.arnonfinalhta;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicManager {

    private static MediaPlayer mediaPlayer;

    public static void startMusic(Context context) {

        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer.create(context, R.raw.bg_music);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.4f, 0.4f);
            mediaPlayer.start();
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static void resumeMusic() {

        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public static void stopMusic() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}


