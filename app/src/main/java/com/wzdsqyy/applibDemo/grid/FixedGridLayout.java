package com.wzdsqyy.applibDemo.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/10/9.
 */

public class FixedGridLayout extends ViewGroup {
    private int numClunms = 1;

    public FixedGridLayout(Context context) {
        super(context);
    }

    public FixedGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getMeasuredWidth();
        getMeasuredHeight();
        getChildCount();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
    }
}
