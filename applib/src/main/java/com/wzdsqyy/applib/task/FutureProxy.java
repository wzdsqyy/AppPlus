package com.wzdsqyy.applib.task;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.Future;

/**
 * Created by Qiuyy on 2016/9/12.
 */
class FutureProxy<H, V> extends UIProxy<H>{
    private Future<V> task;
    private FutureListener<V, H> listener;
    private V v;
    private Throwable ex;
    private boolean isSticky;

    public FutureProxy(@NonNull Future<V> task, FutureListener<V, H> listener, Handler handler, H who, boolean isSticky) {
        super(handler,who);
        this.listener = listener;
        this.task = task;
        this.isSticky=isSticky;
    }

    @Override
    public void run() {
        try {
            if(v==null||!isSticky){
                v = task.get();
            }
        } catch (Exception e) {
            ex = e;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (canCallback()) {
                    listener.onTaskComplete(getWho(), task, v, ex);
                }
            }
        });
    }
}
