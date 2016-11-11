package com.wzdsqyy.test;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.agera.ActivationHandler;
import com.google.android.agera.Function;
import com.google.android.agera.Observable;
import com.google.android.agera.Observables;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;
import com.google.android.agera.UpdateDispatcher;

/**
 * Created by Administrator on 2016/11/11.
 */

public class MainViewModel implements Observable {
    private static final String TAG = "MainViewModel";
    private final Repository<String> hello;
    private UpdateDispatcher dispatcher;

    public MainViewModel(Updatable listener) {
        this.dispatcher = Observables.updateDispatcher();
        hello = Repositories.repositoryWithInitialValue("Hello")
                .observe(this)
                .onUpdatesPerLoop()
                .getFrom(new Supplier<Long>() {
                    @NonNull
                    @Override
                    public Long get() {
                        Log.d(TAG, "get: ");
                        return System.currentTimeMillis();
                    }
                })
                .thenTransform(new Function<Long, String>() {
                    @NonNull
                    @Override
                    public String apply(@NonNull Long input) {
                        Log.d(TAG, "apply: ");
                        return input + "apply";
                    }
                }).compile();
        hello.addUpdatable(listener);
    }

    @Override
    public void addUpdatable(@NonNull Updatable updatable) {
        dispatcher.addUpdatable(updatable);
    }

    public Result<String> getHello() {
        String s = hello.get();
        if(TextUtils.isEmpty(s)){
            Result.failure();
        }
        return Result.success(s);
    }

    @Override
    public void removeUpdatable(@NonNull Updatable updatable) {
        dispatcher.removeUpdatable(updatable);
    }

    public void refreshData(){
        dispatcher.update();
    }
}
