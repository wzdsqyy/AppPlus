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
    private TimerSupport support;


    public ViewHolder(TextView text) {
        this.text = text;
    }

    @Override
    public void onCountDownTick(long time, boolean isFinish) {
        if(!isFinish){
            text.setText(""+time/1000);
        }else{
            text.setText("结书");
        }
    }

    public ViewHolder setSupport(TimerSupport support) {
        this.support = support;
        return this;
    }

    @Override
    public boolean isCancel() {
        return isCancel;
    }

    @Override
    public TimerSupport getTimerSupport() {
        return support;
    }
}