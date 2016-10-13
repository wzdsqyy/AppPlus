package com.wzdsqyy.applib.ui.nested;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/10/13.
 */

public class NestedHelper extends FrameLayout {
    private ViewGroup targetView;
    private DisallowInterceptListener listener;

    private NestedHelper(Context context) {
        super(context);
    }

    public static  NestedHelper newInstance(@NonNull Context context,@NonNull ViewGroup target) {
        NestedHelper fragment = new NestedHelper(context);
        fragment.setTargetView(target);
        return fragment;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (listener != null) {
            if (listener.disallowInterceptTouchEvent(this, ev)) {
                requestDisallowInterceptTouchEvent(true);
            } else {
                requestDisallowInterceptTouchEvent(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public NestedHelper setInterceptTouchListener(DisallowInterceptListener listener) {
        if (targetView == null) {
            throw new RuntimeException("setTargetView Frist");
        }
        this.listener = listener;
        return this;
    }

    public NestedHelper setTargetView(@NonNull ViewGroup target) {
        this.targetView = target;
        ViewGroup parent = (ViewGroup) target.getParent();
        ViewGroup.LayoutParams params = target.getLayoutParams();
        int index=-1;
        if(parent!=null){
            index = parent.indexOfChild(target);
            parent.removeView(target);
        }
        addView(target,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if(parent!=null){
            parent.addView(this, index,params);
        }
        requestDisallowInterceptTouchEvent(true);
        return this;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
