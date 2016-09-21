package com.wzdsqyy.applib.task;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by Qiuyy on 2016/9/12.
 */
class GroupTaskProxy implements Runnable {
    private String key;
    private ArrayList<Future> tasks = new ArrayList<>();
    private GroupListener listener;
    private Throwable ex;
    private boolean isDone = false;
    private boolean isCommit = false;
    private boolean isSticky = false;
    private Handler handler;
    private HashMap map;

    public GroupTaskProxy(String key, boolean isSticky, HashMap<String, GroupTaskProxy> list) {
        this.key = key;
        this.isSticky = isSticky;
        this.map = list;
    }

    private boolean isCommit() {
        return isCommit;
    }

    public boolean isDone() {
        return isDone;
    }

    private ArrayList<Future> getTask() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    public void addTask(Future task) {
        getTask().add(task);
        isDone = false;
    }

    @Override
    public void run() {
        if (tasks != null && !isDone()) {
            Iterator<Future> iterator = tasks.iterator();
            boolean success = true;
            while (iterator.hasNext()) {
                Future future = iterator.next();
                if (success && !future.isDone()) {
                    int n = 1;
                    while (!future.isDone() && n <= 32) {
                        try {
                            Thread.sleep(n * 1000);
                        } catch (InterruptedException e) {
                            ex = e;
                        } finally {
                            n=n*2;
                        }
                    }
                    try {
                        if(!future.isDone()){
                            future.get();
                        }
                    } catch (Exception e1) {
                        ex = e1;
                        success = false;
                    }
                } else if (!success) {
                    future.cancel(true);
                }
            }
            isDone = true;
            notifyListener(map);
        }
    }

    private void notifyListener(HashMap map) {
        isCommit=false;
        if (this.handler != null && listener != null && isDone()) {
            if (map != null && !isSticky) {
                map.remove(key);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onTaskComplete(ex);
                }
            });
        }
    }

    public void setGroupListener(ExecutorService service, GroupListener listener, Handler handler) {
        this.listener = listener;
        this.handler = checkHandler(handler);
        if (!isDone()) {
            if(!isCommit()){
                service.execute(this);
                isCommit=true;
            }
        } else {
            notifyListener(map);
        }
    }

    private Handler checkHandler(Handler handler) {
        if(handler!=null){
            if (Looper.getMainLooper() != handler.getLooper()) {
                throw new IllegalArgumentException("必须是UI线程的handler");
            }
            this.handler=handler;
        }else if(this.handler==null){
            this.handler=new Handler(Looper.getMainLooper());
        }
        return this.handler;
    }
}
