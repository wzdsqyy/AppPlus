package com.wzdsqyy.commonview.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by liaoinstan on 2016/3/11.
 */
public class RefreshLayout extends FrameLayout implements View.OnTouchListener{
    private View.OnTouchListener touchListener;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setOnTouchListener(touchListener);
        return false;
    }
}
