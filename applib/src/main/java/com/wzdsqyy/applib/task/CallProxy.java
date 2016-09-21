package com.wzdsqyy.applib.task;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by Qiuyy on 2016/9/12.
 */
class CallProxy<H, V> extends UIProxy<H> {
    private Callable<V> call;
    private CallListener<V, H> listener;
    private V v;
    private Throwable ex;
    private Future<V> future;

    public CallProxy(@NonNull Callable<V> call, CallListener<V, H> listener, Handler handler, H who, @NonNull ExecutorService service) {
        super(handler, who);
        this.listener = listener;
        this.call = call;
        future = service.submit(call);
    }

    @Override
    public void run() {
        try {
            v = future.get();
        } catch (Exception e) {
            ex = e;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (canCallback()) {
                    listener.onTaskComplete(getWho(), call, v, ex);
                }
            }
        });
    }
}
