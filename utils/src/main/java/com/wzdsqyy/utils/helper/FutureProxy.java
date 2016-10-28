package com.wzdsqyy.utils.helper;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/10/28.
 */

class FutureProxy<T> implements Runnable{
    private Callback<T> callback;
    private Future<T> future;
    private Executor main;

    public FutureProxy(Callback<T> callback, Future<T> future, Executor main) {
        this.callback = callback;
        this.future = future;
        this.main = main;
    }

    public static<T> FutureProxy<T> newInstance(Future<T> future, Callback<T> callback,Executor main) {
        FutureProxy fragment = new FutureProxy(callback,future,main);
        return fragment;
    }

    @Override
    public void run() {
        if(callback==null){
            return;
        }
        if(future!=null&&future.isCancelled()){
            try {
                T result = future.get();
                main.execute(TaskProxy.newInstance(result, callback));
            } catch (Exception e) {
                main.execute(TaskProxy.newInstance(e, callback));
            }
        }
    }
}
