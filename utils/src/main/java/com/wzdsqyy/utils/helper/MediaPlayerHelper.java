package com.wzdsqyy.utils.helper;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Surface;
import android.view.TextureView;

import java.io.File;

/**
 * Created by Administrator on 2016/10/18.
 */

public class MediaPlayerHelper implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mediaPlayer;
    private boolean mPause = true;
    private boolean isStarted = false;

    public static MediaPlayerHelper newInstance(@NonNull MediaPlayer mediaPlayer) {
        MediaPlayerHelper fragment = new MediaPlayerHelper(mediaPlayer);
        return fragment;
    }

    private MediaPlayerHelper(@NonNull MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Surface surface = new Surface(surfaceTexture);
        mediaPlayer.setSurface(surface);
        if (mPause && isStarted) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        mediaPlayer.setSurface(null);
        if (mPause && isStarted) {
            mediaPlayer.pause();
        }
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        int width = player.getVideoWidth();
        int height = player.getVideoHeight();


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
        try {
            if (player.isPlaying()) {
                player.stop();
            }
        } catch (Exception e) {
            player.release();
        }
        player.reset();
        player.setDataSource(path);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setVolume(0, 0); //设置左右音道的声音为0
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.prepareAsync();
        isStarted = true;
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
        isStarted = false;
        player.stop();
        player.reset();
    }

    public void setStreamVolume(Context context, int index) {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && manager.isVolumeFixed()) {
            return;
        }
        int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (maxVolume < index) {
            index = maxVolume;
        } else if (index < 0) {
            index = 0;
        }
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, index, AudioManager.FLAG_PLAY_SOUND);
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
