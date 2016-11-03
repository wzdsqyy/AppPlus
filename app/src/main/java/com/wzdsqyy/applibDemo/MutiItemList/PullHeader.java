package com.wzdsqyy.applibDemo.MutiItemList;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/25.
 */
public class PullHeader extends ImageView implements AppBarLayout.OnOffsetChangedListener{
    private static final String TAG = "PullHeader";
    private AppBarLayout appBarLayout;

    public PullHeader(Context context) {
        this(context, null);
    }

    public PullHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            appBarLayout = (AppBarLayout) parent;
            ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View) parent));
            appBarLayout.addOnOffsetChangedListener(this);
            ViewCompat.requestApplyInsets(this);
            appBarLayout.setExpanded(false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        final ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(this);
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.d(TAG, "onOffsetChanged() called with: appBarLayout = [" + appBarLayout.toString() + "], verticalOffset = [" + verticalOffset + "]");
    }

    public void onUserRelease() {
        if (appBarLayout.getTotalScrollRange() > appBarLayout.getTotalScrollRange() / 2) {
            appBarLayout.setExpanded(true, true);
        } else {
            appBarLayout.setExpanded(false, true);
        }
    }
}
