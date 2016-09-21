package com.wzdsqyy.applib.utils;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/12/17.
 */
public class MyAnimaListener implements Animation.AnimationListener {
    private ViewGroup rootView;
    private ViewGroup animLayout;
    private Animation.AnimationListener listener;

    public MyAnimaListener(ViewGroup rootView, ViewGroup animLayout, Animation.AnimationListener listener) {
        this.listener = listener;
        this.animLayout = animLayout;
        this.rootView = rootView;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (listener != null) {
            listener.onAnimationStart(animation);
        }

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (listener != null) {
            listener.onAnimationEnd(animation);
        }
        if (rootView != null && animLayout != null) {
            rootView.removeView(animLayout);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        if (listener != null) {
            listener.onAnimationRepeat(animation);
        }

    }
}
