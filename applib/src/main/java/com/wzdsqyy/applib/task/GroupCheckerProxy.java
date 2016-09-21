package com.wzdsqyy.applib.task;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Future;

/**
 * Created by Qiuyy on 2016/9/13.
 */
class GroupCheckerProxy<H> extends UIProxy<H> {
    private ArrayList<Future> tasks;
    private GroupListener listener;
    private Throwable ex;
    private boolean done;

    public GroupCheckerProxy(Handler handler, H who) {
        super(handler, who);
    }

    private ArrayList<Future> getTasks() {
        if(tasks==null){
            tasks=new ArrayList<>();
        }
        return tasks;
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
                            n = n * 2;
                        }
                    }
                    try {
                        if (!future.isDone()) {
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
            done = true;
            notifyListener();
        }
    }

    public void addTask(Future task) {
        getTasks().add(task);
        done = false;
    }

    private void notifyListener() {
        if (canCallback()&&listener!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onTaskComplete(ex);
                }
            });
        }
    }

    public boolean isDone() {
        return done;
    }
}
