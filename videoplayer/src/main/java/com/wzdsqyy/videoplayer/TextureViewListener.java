package com.wzdsqyy.videoplayer;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by Administrator on 2016/11/22.
 */

public class TextureViewListener implements TextureView.SurfaceTextureListener {

    private Surface mSurface;
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int i, int i1) {
        mSurface = new Surface(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        surface.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
