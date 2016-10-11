package com.wzdsqyy.applib.utils.helper;

import android.os.AsyncTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2016/9/30.
 */

public class AsyncTaskFixedHelper implements RejectedExecutionHandler {
    private static AsyncTaskFixedHelper helper=new AsyncTaskFixedHelper();
    private ExecutorService executor;

    private AsyncTaskFixedHelper() {
        ThreadPoolExecutor executor= (ThreadPoolExecutor) AsyncTask.THREAD_POOL_EXECUTOR;
        executor.setRejectedExecutionHandler(this);
    }

    public static AsyncTaskFixedHelper getHelper() {
        return helper;
    }

    public ExecutorService getExecutor() {
        return (ExecutorService) AsyncTask.THREAD_POOL_EXECUTOR;
    }

    public synchronized ExecutorService getTempExecutor() {
        if(executor==null||executor.isShutdown()){
            executor= Executors.newCachedThreadPool();
        }
        return executor;
    }

    public void shutdownTemp() {
        if(executor!=null||!executor.isShutdown()){
            executor.shutdown();
        }
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        getTempExecutor().execute(r);
    }
}
