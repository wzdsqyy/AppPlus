package com.wzdsqyy.utils.helper;

import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/10/28.
 */

class TaskProxy<T> implements Runnable{
    private T result;
    private Throwable ex;
    private Callback<T> callback;
    private Future<T> future;

    private TaskProxy(T result, Callback<T> callback) {
        this.result = result;
        this.callback = callback;
    }

    public TaskProxy(Callback<T> callback, Future<T> future) {
        this.callback = callback;
        this.future = future;
    }

    private TaskProxy(Throwable ex, Callback<T> callback) {
        this.ex = ex;
        this.callback = callback;
    }

    public static<T> TaskProxy<T> newInstance(T result, Callback<T> callback) {
        TaskProxy fragment = new TaskProxy(result,callback);
        return fragment;
    }


    public static<T> TaskProxy<T> newInstance(Throwable ex, Callback<T> callback) {
        TaskProxy fragment = new TaskProxy(ex,callback);
        return fragment;
    }

    @Override
    public void run() {
        if(callback==null){
            return;
        }
        mainTask();
    }

    private void mainTask() {
        if(ex!=null||result==null){
            if(callback instanceof ErrorCallback){
                ((ErrorCallback) callback).onFailure(ex);
            }
            return;
        }else {
            callback.onSuccess(result);
        }
        if (callback instanceof FinallyCallback){
            ((FinallyCallback) callback).onFinally();
        }
    }
}
