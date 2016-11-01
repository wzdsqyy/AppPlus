package com.wzdsqyy.utils.helper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/**
 * Created by Administrator on 2016/10/28.
 */
class MainExecutor extends Handler implements Executor {
    private Handler.Callback callback;

    public MainExecutor setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void handleMessage(Message msg) {
        if (callback == null) {
            return;
        }
        callback.handleMessage(msg);
    }

    public MainExecutor() {
        super(Looper.getMainLooper());
    }

    public Handler getMainHandler() {
        return this;
    }

    @Override
    public void execute(Runnable command) {
        post(command);
    }
}
