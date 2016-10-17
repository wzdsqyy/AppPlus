package com.wzdsqyy.applib.utils.helper;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import com.bumptech.glide.GlideBuilder;

import org.greenrobot.eventbus.EventBusBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/9/30.
 */

public class ExecutorHelper implements RejectedExecutionHandler, ThreadFactory {
    private static ExecutorHelper helper = new ExecutorHelper();
    private ThreadPoolExecutor executor;
    private ThreadPoolExecutor expandExecutor;
    private Executor mainExecutor;

    private ExecutorHelper() {
        executor = (ThreadPoolExecutor) AsyncTask.THREAD_POOL_EXECUTOR;
        ;
        executor.setRejectedExecutionHandler(this);
        executor.setThreadFactory(this);
        mainExecutor = new MainExecutor();
    }

    /**
     * 获取UI线程
     *
     * @return
     */
    public Executor getMainExecutor() {
        return mainExecutor;
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
     *
     * @param builder
     * @return
     */
    public GlideBuilder setOkHttpExcutor(GlideBuilder builder) {
        return builder.setResizeService(getExecutor()).setDiskCacheService(getExecutor());
    }


    /**
     * 获取单例
     *
     * @return
     */
    public static ExecutorHelper getHelper() {
        return helper;
    }

    /**
     * 后台任务
     *
     * @return
     */
    public ExecutorService getExecutor() {
        return executor;
    }

    private synchronized ExecutorService getTempExecutor() {
        if (expandExecutor == null || expandExecutor.isShutdown()) {
            expandExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool(this);
        }
        return executor;
    }

    public void shutdownTemp() {
        if (executor != null || !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        getTempExecutor().execute(r);
    }

    private final AtomicInteger mCount = new AtomicInteger(1);

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "ExecutorPool #" + mCount.getAndIncrement());
        thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        return thread;
    }


    private static class MainExecutor implements Executor {
        private Handler mainHandler;
        private Object lockHandler = new Object();

        public Handler getMainHandler() {
            if (mainHandler == null) {
                synchronized (lockHandler) {
                    if (mainHandler == null || mainHandler.getLooper() != Looper.getMainLooper()) {
                        mainHandler = new Handler(Looper.getMainLooper());
                    }
                    return mainHandler;
                }
            }
            return mainHandler;
        }

        @Override
        public void execute(Runnable command) {
            getMainHandler().post(command);
        }
    }
}
