package com.wzdsqyy.applib.ui.nested;

import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/10/13.
 */
public interface DisallowInterceptListener {
    boolean disallowInterceptTouchEvent(NestedHelper v, MotionEvent event);
}
