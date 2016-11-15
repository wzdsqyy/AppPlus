package com.wzdsqyy.commonview.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.DrawableWrapper;

/**
 * Created by Administrator on 2016/11/15.
 */

public class BaseDrawable extends Drawable implements DrawableWrapper, Drawable.Callback {
    private Drawable mDrawable;
    private boolean useBounds=false;
    private int height=0;
    private int width=0;

    public BaseDrawable(Drawable drawable) {
        setWrappedDrawable(drawable);
        if (drawable != null) {
            setBounds(drawable.getBounds());
            useBounds=true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        height=canvas.getHeight();
        width=canvas.getWidth();
        if (mDrawable != null) {
            mDrawable.draw(canvas);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        setBounds(bounds);
        if (mDrawable == null) {
            return;
        }
        mDrawable.setBounds(bounds);
    }

    @Override
    public void setChangingConfigurations(int configs) {
        if (mDrawable == null) {
            super.setChangingConfigurations(configs);
            return;
        }
        mDrawable.setChangingConfigurations(configs);
    }

    @Override
    public int getChangingConfigurations() {
        if (mDrawable == null) {
            return super.getChangingConfigurations();
        }
        return mDrawable.getChangingConfigurations();
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        if (mDrawable == null) {
            super.setFilterBitmap(filter);
            return;
        }
        mDrawable.setFilterBitmap(filter);
    }

    @Override
    public void setAlpha(int alpha) {
        if (mDrawable == null) {
            return;
        }
        mDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (mDrawable == null) {
            return;
        }
        mDrawable.setColorFilter(cf);
    }

    @Override
    public boolean isStateful() {
        if (mDrawable == null) {
            return super.isStateful();
        }
        return mDrawable.isStateful();
    }

    @Override
    public boolean setState(final int[] stateSet) {
        if (mDrawable == null) {
            return setState(stateSet);
        }
        return mDrawable.setState(stateSet);
    }

    @Override
    public int[] getState() {
        if (mDrawable == null) {
            return super.getState();
        }
        return mDrawable.getState();
    }

    public void jumpToCurrentState() {
        if (mDrawable == null) {
            super.jumpToCurrentState();
            return;
        }
        DrawableCompat.jumpToCurrentState(mDrawable);
    }

    @Override
    public Drawable getCurrent() {
        if (mDrawable == null) {
            return this;
        }
        return mDrawable.getCurrent();
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        if (mDrawable == null) {
            return super.setVisible(visible, restart);
        }
        return super.setVisible(visible, restart) || mDrawable.setVisible(visible, restart);
    }

    @Override
    public int getOpacity() {
        if (mDrawable == null) {
            return PixelFormat.UNKNOWN;
        }
        return mDrawable.getOpacity();
    }

    @Override
    public Region getTransparentRegion() {
        if (mDrawable == null) {
            return super.getTransparentRegion();
        }
        return mDrawable.getTransparentRegion();
    }

    @Override
    public int getIntrinsicWidth() {
        if (mDrawable == null) {
            return super.getIntrinsicWidth();
        }
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        if (mDrawable == null) {
            return super.getIntrinsicHeight();
        }
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public int getMinimumWidth() {
        if (mDrawable == null) {
            return super.getMinimumWidth();
        }
        return mDrawable.getMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        if (mDrawable == null) {
            return super.getMinimumHeight();
        }
        return mDrawable.getMinimumHeight();
    }

    @Override
    public boolean getPadding(Rect padding) {
        if (mDrawable == null) {
            return super.getPadding(padding);
        }
        return mDrawable.getPadding(padding);
    }

    /**
     * {@inheritDoc}
     */
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    /**
     * {@inheritDoc}
     */
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    /**
     * {@inheritDoc}
     */
    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    @Override
    protected boolean onLevelChange(int level) {
        if (mDrawable == null) {
            return super.setLevel(level);
        }
        return mDrawable.setLevel(level);
    }

    @Override
    public void setAutoMirrored(boolean mirrored) {
        if (mDrawable == null) {
            DrawableCompat.setAutoMirrored(this, mirrored);
            return;
        }
        DrawableCompat.setAutoMirrored(mDrawable, mirrored);
    }

    @Override
    public boolean isAutoMirrored() {
        if (mDrawable == null) {
            return DrawableCompat.isAutoMirrored(this);
        }
        return DrawableCompat.isAutoMirrored(mDrawable);
    }

    @Override
    public void setTint(int tint) {
        if (mDrawable == null) {
            DrawableCompat.setTint(this, tint);
            return;
        }
        DrawableCompat.setTint(mDrawable, tint);
    }

    @Override
    public void setTintList(ColorStateList tint) {
        if (mDrawable == null) {
            DrawableCompat.setTintList(this, tint);
            return;
        }
        DrawableCompat.setTintList(mDrawable, tint);
    }

    public int getWidth(){
        if(useBounds){
            return getBounds().width();
        }
        return width;
    }

    public int getHeight(){
        if(useBounds){
            return getBounds().width();
        }
        return height;
    }

    @Override
    public void setTintMode(PorterDuff.Mode tintMode) {
        if (mDrawable == null) {
            DrawableCompat.setTintMode(this, tintMode);
            return;
        }
        DrawableCompat.setTintMode(mDrawable, tintMode);
    }

    @Override
    public void setHotspot(float x, float y) {
        if (mDrawable == null) {
            DrawableCompat.setHotspot(this, x, y);
            return;
        }
        DrawableCompat.setHotspot(mDrawable, x, y);
    }

    @Override
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        if (mDrawable == null) {
            DrawableCompat.setHotspotBounds(this, left, top, right, bottom);
            return;
        }
        DrawableCompat.setHotspotBounds(mDrawable, left, top, right, bottom);
    }

    @Override
    public Drawable getWrappedDrawable() {
        return mDrawable;
    }

    @Override
    public void setWrappedDrawable(Drawable drawable) {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
        }
        mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
    }
}
