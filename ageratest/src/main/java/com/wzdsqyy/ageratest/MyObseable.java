package com.wzdsqyy.ageratest;

import android.support.annotation.NonNull;

import com.google.android.agera.ActivationHandler;
import com.google.android.agera.Observables;
import com.google.android.agera.Updatable;
import com.google.android.agera.UpdateDispatcher;

/**
 * Created by Administrator on 2016/10/17.
 */

public class MyObseable implements ActivationHandler {

    private final UpdateDispatcher updateDispatcher;

    public MyObseable(UpdateDispatcher updateDispatcher) {
        this.updateDispatcher = Observables.updateDispatcher(this);
    }
    public void addListener(Updatable listener) {
        updateDispatcher.addUpdatable(listener);
    }
    public void removeListener(Updatable listener) {
        updateDispatcher.removeUpdatable(listener);
    }

    @Override
    public void observableActivated(@NonNull UpdateDispatcher caller) {

    }

    @Override
    public void observableDeactivated(@NonNull UpdateDispatcher caller) {

    }
}
