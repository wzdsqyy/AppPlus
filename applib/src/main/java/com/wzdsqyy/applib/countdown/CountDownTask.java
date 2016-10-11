package com.wzdsqyy.applib.countdown;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/9/21.
 */

public class CountDownTask implements Runnable,View.OnAttachStateChangeListener{
    private volatile boolean isFinish=false;
    private Handler handler;
    private volatile CountDownListener listener;
    private volatile TimerSupport timerSupport;
    private ArrayList<CountDownListener> listeners;

    private ArrayList<CountDownListener> getCountDownListeners() {
        if(listeners==null){
            listeners= new ArrayList<>();
        }
        return listeners;
    }

    private void addCountDownListener(CountDownListener listener) {
        if(!getCountDownListeners().contains(listener)){
            getCountDownListeners().add(listener);
        }
    }

    private void notifyListeners(long haveTime,boolean isFinish) {
        Iterator<CountDownListener> iterator = getCountDownListeners().iterator();
        while (iterator.hasNext()){
            CountDownListener listener = iterator.next();
            listener.onCountDownTick((int) (haveTime / 1000), isFinish);
        }
    }

    private void removeCountDownListener(CountDownListener listener) {
        getCountDownListeners().remove(listener);
    }

    public void setCountDownListener(CountDownListener listener) {
        this.listener = listener;
    }

    public CountDownTask start(TimerSupport timerSupport) {
        this.timerSupport=timerSupport;
        handler.removeCallbacks(this);
        handler.post(this);
        return this;
    }

    @Override
    public void run() {
        if(timerSupport==null&&handler!=null){
            handler.removeCallbacks(this,this);
            return;
        }
        long haveTime=timerSupport.getEndTime()-System.currentTimeMillis();
        isFinish = haveTime < 0;
        if (listener != null) {
            listener.onCountDownTick((int) (haveTime / 1000), isFinish);
        }
        if(listeners!=null){
            notifyListeners(haveTime,isFinish);
        }
        if(!isFinish){
            if (listener == null||(listener!=null&& !listener.isCancel())) {
                Message message = Message.obtain(handler, this);
                message.obj=this;
                handler.sendMessageDelayed(message, timerSupport.countDownInterval());
            } else {
                handler.removeCallbacks(this);
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        handler=v.getHandler();
        handler.post(this);
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        handler.removeCallbacksAndMessages(this);
    }
}
