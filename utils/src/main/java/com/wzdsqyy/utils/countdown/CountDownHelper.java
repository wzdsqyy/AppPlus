package com.wzdsqyy.utils.countdown;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2016/10/11.
 */

public class CountDownHelper implements Runnable{
    private CopyOnWriteArrayList<CountDownListener> listeners;
    private final int interval;
    private Handler handler;
    private volatile boolean start=false;

    public CountDownHelper(@NonNull int interval, Handler handler) {
        this.interval = interval;
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        this.handler = handler;
    }

    public CountDownHelper(@NonNull int interval) {
        this(interval, null);
    }

    public void addCountDownListener(CountDownListener listener) {
        if (!getCountDownListeners().contains(listener)) {
            getCountDownListeners().add(listener);
        }
        if(getCountDownListeners().size()>0){
            start();
        }
    }

    private boolean notifyListeners() {
        if (getCountDownListeners().size() == 0) {
            return false;
        }
        Iterator<CountDownListener> iterator = getCountDownListeners().iterator();
        while (iterator.hasNext()) {
            CountDownListener listener = iterator.next();
            if (listener.isCancel()) {
                removeCountDownListener(listener);
                continue;
            }
            TimerSupport support = listener.getTimerSupport();
            if (support == null) {
                continue;
            }
            long time = support.endTime() - System.currentTimeMillis();
            listener.onCountDownTick(time,time<0);
            if (time < 0) {
                removeCountDownListener(listener);
            }
        }
        return true;
    }

    private void removeCountDownListener(CountDownListener listener) {
        getCountDownListeners().remove(listener);
    }

    private CopyOnWriteArrayList<CountDownListener> getCountDownListeners() {
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<>();
        }
        return listeners;
    }

    public void start() {
        if(start){
            return;
        }
        handler.post(this);
        start=true;
    }

    public void stop() {
        if(!start){
            return;
        }
        handler.removeCallbacks(this);
        start=false;
    }

    @Override
    public void run() {
        boolean notify = notifyListeners();
        if (notify) {
            handler.postDelayed(this, interval);
        }
    }
}
