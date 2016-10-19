package com.wzdsqyy.applib.countdown;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/10/11.
 */

public class CountDownHelper implements Runnable{
    private ArrayList<CountDownListener> listeners = new ArrayList<>();
    private final int interval;
    private Handler handler;

    public CountDownHelper(@NonNull int interval) {
        this.interval = interval;
        if(handler==null){
            handler=new Handler(Looper.getMainLooper());
        }
    }

    private void addCountDownListener(CountDownListener listener) {
        if (!getCountDownListeners().contains(listener)) {
            getCountDownListeners().add(listener);
        }
    }

    private boolean notifyListeners() {
        if(getCountDownListeners().size()==0){
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
            if(support ==null){
                continue;
            }
            boolean finish = support.isFinish();
            if(finish){
                removeCountDownListener(listener);
            }
            listener.onCountDownTick(finish);
        }
        return true;
    }

    private void removeCountDownListener(CountDownListener listener) {
        getCountDownListeners().remove(listener);
    }

    private ArrayList<CountDownListener> getCountDownListeners() {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        return listeners;
    }

    @Override
    public void run() {
        boolean notify = notifyListeners();
        if(notify){
            handler.postDelayed(this,interval);
        }
    }
}
