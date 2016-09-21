package com.wzdsqyy.applib.task;

import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

/**
 * Created by Qiuyy on 2016/9/12.
 */
abstract class UIProxy<H> implements Runnable {
    private Handler handler;
    private WeakReference<H> whoRef;

    public UIProxy(Handler handler, H who) {
        this.handler = checkHandler(handler);
        if (who != null) {
            this.whoRef = new WeakReference<H>(who);
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

    void runOnUiThread(Runnable task){
        this.handler.post(task);
    }

    H getWho(){
        if(whoRef==null){
            return null;
        }else{
            return whoRef.get();
        }
    }

    boolean canCallback(){
        if(whoRef==null){
            return true;
        }else{
            return whoRef.get()!=null;
        }
    }
}
