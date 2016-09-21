package com.wzdsqyy.applib.countdown;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2016/9/21.
 */

class CountDownTask implements Runnable {
    private long endtime;
    private boolean isFinish;
    private Handler handler;
    private CountDownListener listener;
    private TimerSupport timerSupport;
    private Object token;

    public CountDownTask(@NonNull Handler handler, @NonNull Object token, @NonNull TimerSupport timerSupport) {
        this(handler, token, null, timerSupport);
    }

    public CountDownTask(@NonNull Handler handler, @NonNull Object token, CountDownListener listener, @NonNull TimerSupport timerSupport) {
        this.handler = handler;
        this.listener = listener;
        this.token = token;
        this.timerSupport = timerSupport;
        endtime = System.currentTimeMillis() + timerSupport.getTotalTime() * 1000;
    }

    public void setListener(CountDownListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        long haveTime = endtime - System.currentTimeMillis();
        isFinish = haveTime < 0;
        if (listener != null) {
            listener.onCountDownTick((int) (haveTime / 1000), isFinish);
        }
        if(!isFinish){
            if (listener!=null&& !listener.isCancel() || listener == null) {
                Message message = Message.obtain(handler, this);
                message.obj = token;
                handler.sendMessageDelayed(message, timerSupport.countDownInterval());
            } else {
                handler.removeCallbacks(this, token);
            }
        }
    }
}
