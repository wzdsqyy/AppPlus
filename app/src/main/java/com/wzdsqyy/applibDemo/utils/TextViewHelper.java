package com.wzdsqyy.applibDemo.utils;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/15.
 */

public class TextViewHelper implements View.OnTouchListener,View.OnClickListener{
    private TextView target;
    private float mDownX,mDownY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==target){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mDownX=event.getX();
                    mDownY=event.getY();
                    break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
