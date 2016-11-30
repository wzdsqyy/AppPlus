package com.wzdsqyy.floatball;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/11/30.
 */
class StatusBarLayout extends LinearLayout {

    private View status;

    public StatusBarLayout(Context context) {
        this(context, null);
    }

    public StatusBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int height = getStatusBarHeight(context);
        status = new View(context);
        LayoutParams params =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        status.setLayoutParams(params);
        addView(status,0);
        setFitsSystemWindows(true);
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public StatusBarLayout setStstatusatusColor(@ColorInt int color) {
        status.setBackgroundColor(color);
        return this;
    }
}
