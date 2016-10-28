package com.wzdsqyy.utils.helper;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by Administrator on 2016/10/28.
 */
class MainExecutor implements Executor {
    private Handler mainHandler;

    public synchronized Handler getMainHandler() {
        if (mainHandler == null) {
            mainHandler = new Handler(Looper.getMainLooper());
        } else if (mainHandler.getLooper() != Looper.getMainLooper()) {
            mainHandler = new Handler(Looper.getMainLooper());
        }
        return mainHandler;
    }

    @Override
    public void execute(Runnable command) {
        getMainHandler().post(command);
    }
}
