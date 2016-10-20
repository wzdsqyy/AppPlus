package com.wzdsqyy.utils.helper;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by Administrator on 2016/10/18.
 */

public class TextureViewHelper implements TextureView.SurfaceTextureListener {
    private MediaPlayer mediaPlayer;

    public static TextureViewHelper newInstance(MediaPlayer mediaPlayer) {
        TextureViewHelper fragment = new TextureViewHelper(mediaPlayer);
        return fragment;
    }

    private TextureViewHelper(@NonNull MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Surface surface = new Surface(surfaceTexture);
        mediaPlayer.setSurface(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        mediaPlayer.stop();
        mediaPlayer.reset();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
