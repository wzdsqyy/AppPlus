package com.wzdsqyy.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Qiuyy on 2016/9/13.
 */
public class ViewUtils {
    public static boolean toViewParent(@Nullable ViewGroup container, @Nullable View target) {
        if (container != null && target != null && target.getParent() != null) {
            ViewGroup parent = (ViewGroup) target.getParent();
            int groupIndex = parent.indexOfChild(target);
            parent.removeView(target);
            if (target.getLayoutParams() != null) {
                container.setLayoutParams(target.getLayoutParams());
            }
            parent.addView(container, groupIndex);
            container.addView(target, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return true;
        }
        return false;
    }

    public static View getFullScrenAnimatorView(View view, Activity activity){
        view.buildDrawingCache(true);
        Bitmap cache = view.getDrawingCache(true);
        final ViewGroup group = (ViewGroup) activity.getWindow().getDecorView();
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        final View item = new View(view.getContext());
        ViewGroup.MarginLayoutParams itemLayout = new ViewGroup.MarginLayoutParams(view.getWidth(),view.getHeight());
        item.setBackgroundDrawable(new BitmapDrawable(cache));
        group.addView(item,itemLayout);
        item.setX(rect.left);
        item.setY(rect.top);
        item.animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                group.removeView(item);
            }
        });
        return item;
    }

    public static void safeSetOnClickListener(@Nullable View target, @Nullable View.OnClickListener listener) {
        if (target != null) {
            target.setOnClickListener(listener);
        }
    }

    public static void safeSetOnClickListener(@IdRes int viewId, @Nullable View parent, @Nullable View.OnClickListener listener) {
        if (parent != null) {
            safeSetOnClickListener(parent.findViewById(viewId), listener);
        }
    }

    public static void safeSetOnClickListener(@IdRes int viewId, @Nullable Activity activity, @Nullable View.OnClickListener listener) {
        if (activity != null) {
            safeSetOnClickListener(activity.findViewById(viewId), listener);
        }
    }

    public static void removeParent(View v) {
        if(v.getParent()!=null){
            ((ViewGroup)v.getParent()).removeView(v);
        }
    }
}
