package com.wzdsqyy.applibDemo.countdown;

import android.widget.TextView;

import com.wzdsqyy.applib.countdown.CountDownHelper;
import com.wzdsqyy.applib.countdown.CountDownListener;
import com.wzdsqyy.applib.countdown.ListTimerSupport;

/**
 * Created by Administrator on 2016/9/21.
 */

public class ViewHolder implements CountDownListener{
    TextView text;
    private ListTimerSupport timerSupport;
    private CountDownHelper helper;
    int possion;

    public void setTimerSupport(ListTimerSupport timerSupport) {
        this.timerSupport = timerSupport;
    }

    public ListTimerSupport getTimerSupport() {
        return timerSupport;
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
        return false;
    }
}