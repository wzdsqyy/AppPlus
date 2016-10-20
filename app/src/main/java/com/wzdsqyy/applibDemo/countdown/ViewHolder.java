package com.wzdsqyy.applibDemo.countdown;

import android.widget.TextView;

import com.wzdsqyy.utils.countdown.CountDownListener;
import com.wzdsqyy.utils.countdown.TimerSupport;


/**
 * Created by Administrator on 2016/9/21.
 */

public class ViewHolder implements CountDownListener {
    TextView text;
    private volatile boolean isCancel=false;


    public ViewHolder(TextView text) {
        this.text = text;
    }

    public void onCountDownTick(int totalTime, boolean isFinish) {

    }

    @Override
    public void onCountDownTick(boolean isFinish) {
        if(!isFinish){

        }else{
            text.setText("结书");
        }
    }

    @Override
    public boolean isCancel() {
        return isCancel;
    }

    @Override
    public TimerSupport getTimerSupport() {
        return null;
    }
}