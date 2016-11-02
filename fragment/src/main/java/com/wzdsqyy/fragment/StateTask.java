package com.wzdsqyy.fragment;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2016/11/2.
 */

public class StateTask implements Runnable {
    FragmentTransaction transaction;

    public StateTask(@NonNull FragmentTransaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void run() {
        transaction.commit();
    }
}
