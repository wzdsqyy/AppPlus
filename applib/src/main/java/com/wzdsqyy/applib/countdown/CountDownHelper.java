package com.wzdsqyy.applib.countdown;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Qiuyy on 2016/9/10.
 */
public class CountDownHelper<T extends View> implements View.OnAttachStateChangeListener{
    private Handler handler;
    private SparseArray<CountDownTask> listeners;

    public CountDownHelper(@NonNull T view) {
        view.addOnAttachStateChangeListener(this);
    }

    public void startCountDownListener(@NonNull TimerSupport timerSupport, CountDownListener listener) {
        if(handler==null){
            handler=new Handler(Looper.getMainLooper());
        }
        Message message = Message.obtain(handler, new CountDownTask(handler,this,listener, timerSupport));
        handler.sendMessage(message);
    }

    private SparseArray<CountDownTask> getListeners() {
        if(listeners==null){
            listeners=new SparseArray<>();
        }
        return listeners;
    }

    public void clearListener(@NonNull ListTimerSupport timerSupport) {
        bindListCountDownListener(timerSupport,null);
    }

    public void bindListCountDownListener(@NonNull ListTimerSupport timerSupport, CountDownListener listener) {
        if(handler==null){
            handler=new Handler(Looper.getMainLooper());
        }
        CountDownTask task = getListeners().get(timerSupport.getTimerSupportPossion());
        if(task==null){
            task=new CountDownTask(handler,this,timerSupport);
        }
        task.setListener(listener);
        Message message = Message.obtain(handler,task);
        handler.sendMessage(message);
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        this.handler.removeCallbacksAndMessages(this);
    }
}
