package com.wzdsqyy.utils.helper;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by Administrator on 2016/10/18.
 */

public class MediaPlayerHelper implements MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnCompletionListener {
    @Override
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    @Override
    public void onSeekComplete(MediaPlayer player) {
        player.start();
    }

    public void start(@NonNull MediaPlayer player, @NonNull File local) throws Exception {
        start(player, local.getAbsolutePath());
    }

    public void start(@NonNull MediaPlayer player, @NonNull String path) throws Exception {
        checkBeforePlay(player);
        player.setDataSource(path);
        startPlayMedia(player);
    }

    private void checkBeforePlay(@NonNull MediaPlayer player) {
        if (player.isPlaying()
                || player.isLooping()) {
            player.stop();
            player.reset();
        }
    }

    private void startPlayMedia(@NonNull MediaPlayer player) {
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setVolume(0, 0); //设置左右音道的声音为0
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.prepareAsync();
    }

    public void seekTo(@NonNull MediaPlayer player, int msec) {
        if (player.isPlaying()) {
            player.pause();
        }
        int duration = player.getDuration();
        if (msec < 0) {
            msec = 0;
        } else if (msec > duration) {
            msec = duration;
        }
        player.seekTo(msec);
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        player.stop();
        player.reset();
    }

    public void setStreamVolume(Context context, int index) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return;
        }
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (manager.isVolumeFixed()) {
            return;
        } else {
            int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            if (maxVolume < index) {
                index = maxVolume;
            } else if (index < 0) {
                index = 0;
            }
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, index, AudioManager.FLAG_PLAY_SOUND);
        }
    }
}
