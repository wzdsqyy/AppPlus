package com.wzdsqyy.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 没有继承HorizontalScrollView不能滑动,对于ViewPager无依赖
 */
public class CommonTabLayout extends FrameLayout {


    public CommonTabLayout(Context context) {
        this(context, null);
    }

    public CommonTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int sp2px(float sp) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

}
