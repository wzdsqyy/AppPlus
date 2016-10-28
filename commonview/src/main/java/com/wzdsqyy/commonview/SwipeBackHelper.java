package com.wzdsqyy.commonview;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/10/26.
 */

public class SwipeBackHelper implements SwipeBackLayout.SwipeBackListener {
    private static final String TAG = "SwipeBackHelper";
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    public SwipeBackHelper setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        swipeBackLayout.setDragEdge(dragEdge);
        return this;
    }

    public static SwipeBackHelper newInstance() {
        SwipeBackHelper fragment = new SwipeBackHelper();
        return fragment;
    }

    private RelativeLayout getContainer(Context context) {
        RelativeLayout container = new RelativeLayout(context);
        swipeBackLayout = new SwipeBackLayout(context);
        swipeBackLayout.setOnSwipeBackListener(this);
        ivShadow = new ImageView(context);
        ivShadow.setBackgroundColor(0x7d000000);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        return container;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        ivShadow.setAlpha(1 - fractionScreen);
    }


    public SwipeBackHelper attach(Activity activity) {
        Window window = activity.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
        View content = window.getDecorView().findViewById(android.R.id.content);
        ViewGroup parent = (ViewGroup) content.getParent();
        ViewGroup.LayoutParams params = content.getLayoutParams();
        int index = parent.indexOfChild(content);
        parent.removeView(content);
        RelativeLayout container = getContainer(activity);
        swipeBackLayout.addView(content);
        parent.addView(container, index, params);
        return this;
    }
}
