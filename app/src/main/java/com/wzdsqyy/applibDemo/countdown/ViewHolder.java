package com.wzdsqyy.applibDemo.countdown;

import android.widget.TextView;

import com.wzdsqyy.applib.countdown.CountDownListener;
import com.wzdsqyy.applib.countdown.CountDownTask;
import com.wzdsqyy.applib.countdown.TimerSupport;

/**
 * Created by Administrator on 2016/9/21.
 */

public class ViewHolder implements CountDownListener{
    TextView text;
    private CountDownTask task;
    private volatile boolean isCancel=false;

    private CountDownTask getTask() {
        if(task==null){
//            task=new CountDownTask(this);
        }
        return task;
    }

    public ViewHolder setTimerSupport(TimerSupport timerSupport) {
        getTask().start(timerSupport);
        return this;
    }

    public ViewHolder(TextView text) {
        this.text = text;
    }

    public void onCountDownTick(int totalTime, boolean isFinish) {
        if(!isFinish){
            text.setText(totalTime+"s");
        }else{
            text.setText("结书");
        }
    }

    @Override
    public boolean isCancel() {
        return isCancel;
    }
}