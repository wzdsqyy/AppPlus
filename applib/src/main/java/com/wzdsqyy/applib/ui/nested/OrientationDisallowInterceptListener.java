package com.wzdsqyy.applib.ui.nested;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/10/13.
 */

public class OrientationDisallowInterceptListener extends GestureDetector.SimpleOnGestureListener implements DisallowInterceptListener {
    private GestureDetector mGestureDetector;
    private boolean isHorizontal = true;
    private boolean mIntercept;

    public OrientationDisallowInterceptListener setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
        return this;
    }

    public OrientationDisallowInterceptListener(Context context) {
        this.mGestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (isHorizontal) {
            return (Math.abs(distanceY) < Math.abs(distanceX));
        } else {
            return (Math.abs(distanceY) > Math.abs(distanceX));
        }
    }

    @Override
    public boolean disallowInterceptTouchEvent(NestedHelper v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
