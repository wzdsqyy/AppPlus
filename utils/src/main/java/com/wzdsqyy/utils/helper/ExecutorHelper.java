package com.wzdsqyy.utils.helper;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;

import org.greenrobot.eventbus.EventBusBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/9/30.
 */

public class ExecutorHelper implements RejectedExecutionHandler, ThreadFactory {
    private static ExecutorHelper helper;
    private ThreadPoolExecutor executor;
    private ThreadPoolExecutor expandExecutor;
    private MainExecutor mainHandler;

    private ExecutorHelper() {
        executor =  (ThreadPoolExecutor) AsyncTask.THREAD_POOL_EXECUTOR;
        executor.setRejectedExecutionHandler(this);
        executor.setThreadFactory(this);
        mainHandler = new MainExecutor();
    }

    public ExecutorHelper setCallback(Handler.Callback callback) {
        getMainHandler().setCallback(callback);
        return this;
    }

    ReentrantLock lockHandler=new ReentrantLock();
    public MainExecutor getMainHandler() {
        if(mainHandler.getLooper()==Looper.getMainLooper()){
            return mainHandler;
        }
        lockHandler.lock();
        mainHandler=new MainExecutor();
        lockHandler.unlock();
        return mainHandler;
    }

    public <R> void runOnMainExcutor(@NonNull Callback<R> callback, R result) {
        getMainExecutor().execute(TaskProxy.newInstance(result, callback));
    }

    public <R> Future<R> runOnExcutor(@NonNull Callable<R> call) {
        return getExecutor().submit(call);
    }

    public <R> void runOnExcutor(@NonNull Future<R> future, @NonNull Callback<R> callback) {
        getExecutor().execute(FutureProxy.newInstance(future, callback, getMainExecutor()));
    }

    public <R> void runOnMainExcutor(@NonNull ErrorCallback<R> callback, Throwable ex) {
        getMainExecutor().execute(TaskProxy.newInstance(ex, callback));
    }

    public synchronized Executor getMainExecutor() {
        if(mainHandler ==null|| mainHandler.getLooper()!=Looper.getMainLooper()){
            mainHandler =new MainExecutor();
        }
        return mainHandler;
    }

    /**
     * EventBus3.0的线程池设置
     *
     * @param builder
     * @return
     */
    public EventBusBuilder setEventBusExcutor(EventBusBuilder builder) {
        return builder.executorService(getExecutor());
    }


    /**
     * OkHttp3.0的线程池设置
     *
     * @param builder
     * @return
     */
    public OkHttpClient.Builder setOkHttpExcutor(OkHttpClient.Builder builder) {
        return builder.dispatcher(new Dispatcher(getExecutor()));
    }

    /**
     * Glide的线程池设置
     * @param builder
     * @return
     */
    public GlideBuilder setOkHttpExcutor(GlideBuilder builder) {
        return builder.setResizeService(getExecutor()).setDiskCacheService(getExecutor());
    }

    public static synchronized ExecutorHelper getHelper() {
        if (helper == null) {
            helper = new ExecutorHelper();
        }
        return helper;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    private synchronized ExecutorService getTempExecutor() {
        if (expandExecutor == null || expandExecutor.isShutdown()) {
            expandExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool(this);
        }
        return expandExecutor;
    }

    public void release() {
        if (executor != null) {
            executor.shutdown();
        }
        if (expandExecutor != null) {
            expandExecutor.shutdown();
        }
        helper = null;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        getTempExecutor().execute(r);
    }

    private final AtomicInteger mCount = new AtomicInteger(1);

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "ExecutorHelper #" + mCount.getAndIncrement());
        thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        return thread;
    }
}
