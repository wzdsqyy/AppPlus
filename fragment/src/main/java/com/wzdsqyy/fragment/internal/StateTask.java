package com.wzdsqyy.fragment.internal;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2016/11/2.
 */

class StateTask implements Runnable {
    FragmentTransaction transaction;
    boolean commitNow=false;

    public StateTask(@NonNull FragmentTransaction transaction,boolean commitNow) {
        this.transaction = transaction;
        this.commitNow = commitNow;
    }

    @Override
    public void run() {
        if(commitNow){
            transaction.commitNow();
        }else {
            transaction.commit();
        }
    }
}
