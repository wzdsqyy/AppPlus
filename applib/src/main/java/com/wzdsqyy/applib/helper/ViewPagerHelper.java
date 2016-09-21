package com.wzdsqyy.applib.helper;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/9/20.
 */

public class ViewPagerHelper implements View.OnTouchListener{
    private int count;
    private ViewGroup parent;
    private float mMax=-1;
    private boolean disallowIntercept;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(parent==null){
            return false;
        }
        boolean disallow=disallowIntercept;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(v instanceof ViewPager){
                    mMax=v.getMeasuredWidth()*count;
                }else{
                    mMax=v.getMeasuredWidth();
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float mDownX = v.getTranslationX();
                if(mDownX==0||mMax==v.getTranslationX()){
                    disallow=false;
                }else {
                    disallow=true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mDownX=v.getTranslationX();
                if(mDownX>=0||mMax>v.getTranslationX()){
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        if(disallow!=disallowIntercept){
            v.getParent().requestDisallowInterceptTouchEvent(disallow);
            event.setAction(MotionEvent.ACTION_DOWN);
            disallowIntercept=disallow;
            return !parent.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setChildView(ViewPager child){
        child.setOnTouchListener(this);
        count=child.getAdapter().getCount();
        child.requestDisallowInterceptTouchEvent(true);
        disallowIntercept=true;
        parent = (ViewGroup) child.getParent();
    }
}
