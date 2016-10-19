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

public class ExecutorHelper implements RejectedExecutionHandler, ThreadFactory{
    private static ExecutorHelper helper;
    private ThreadPoolExecutor executor;
    private ThreadPoolExecutor expandExecutor;
    private Executor mainExecutor;

    private ExecutorHelper() {
        executor = (ThreadPoolExecutor) AsyncTask.THREAD_POOL_EXECUTOR;
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
        for (; ; ) {
            if (helper == null) {
                helper = new ExecutorHelper();
            }
            return helper;
        }
    }

    /**
     * 后台任务
     *
     * @return
     */
    public ExecutorService getExecutor() {
        return executor;
    }

    private ExecutorService getTempExecutor() {
        for (; ; ) {
            if (expandExecutor == null || expandExecutor.isShutdown()) {
                expandExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool(this);
                return expandExecutor;
            }
        }
    }

    public ExecutorHelper shutdownTemp() {
        if (executor != null || !executor.isShutdown()) {
            executor.shutdown();
        }
        return this;
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
        Thread thread = new Thread(r, "ExecutorPool #" + mCount.getAndIncrement());
        thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        return thread;
    }

    public ExecutorHelper execute(Runnable command) {
        if (command instanceof UiMain) {
            getMainExecutor().execute(command);
        } else {
            getExecutor().execute(command);
        }
        return this;
    }

    private static class MainExecutor implements Executor {
        private Handler mainHandler;

        public Handler getMainHandler() {
            for (; ; ) {
                if (mainHandler == null) {
                    mainHandler = new Handler(Looper.getMainLooper());
                }
                if (mainHandler.getLooper() != Looper.getMainLooper()) {
                    mainHandler = new Handler(Looper.getMainLooper());
                }
                return mainHandler;
            }
        }

        @Override
        public void execute(Runnable command) {
            getMainHandler().post(command);
        }
    }
}
