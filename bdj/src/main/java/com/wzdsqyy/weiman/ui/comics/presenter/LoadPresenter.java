package com.wzdsqyy.weiman.ui.comics.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.agera.Observable;
import com.google.android.agera.Observables;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import com.google.android.agera.UpdateDispatcher;

/**
 * Created by Administrator on 2016/11/11.
 */

public class LoadPresenter<T> implements Observable, Updatable{
    private CallBack<T> callBack;
    private UpdateDispatcher dispatcher;
    private Repository<Result<T>> compile;
    private boolean loading=false;
    private boolean started =false;

    public LoadPresenter(CallBack<T> callBack) {
        dispatcher= Observables.updateDispatcher();
        this.callBack=callBack;
    }

    public LoadPresenter setCompile(Repository<Result<T>> compile) {
        this.compile = compile;
        return this;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isSuccessLoad(){
        if(compile!=null&&compile.get().succeeded()){
            return true;
        }
        return false;
    }

    public boolean startLoading(){
        if(loading){
            return false;
        }
        if(!init()){
            dispatcher.update();
        }
        loading=true;
        return true;
    }

    public boolean init(){
        if(!started&&compile!=null){
            started =true;
            compile.addUpdatable(this);
            return true;
        }
        return false;
    }

    public boolean release(){
        if(started&&compile!=null){
            started =false;
            compile.removeUpdatable(this);
            return true;
        }
        return false;
    }


    @Override
    public void addUpdatable(@NonNull Updatable updatable) {
        dispatcher.addUpdatable(updatable);
    }

    @Override
    public void removeUpdatable(@NonNull Updatable updatable) {
        dispatcher.removeUpdatable(updatable);
    }

    @Override
    public void update() {
        loading=false;
        if(compile==null||callBack==null){
            return;
        }
        if(compile.get().succeeded()){
            callBack.onSuccess(compile.get().get());
        }else {
            callBack.onLoadError(compile.get().getFailure());
        }
    }
}
