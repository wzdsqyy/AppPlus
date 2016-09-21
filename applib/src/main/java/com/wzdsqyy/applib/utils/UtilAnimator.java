package com.wzdsqyy.applib.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Qiuyy on 2016/8/22.
 */
public class UtilAnimator {

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
}
