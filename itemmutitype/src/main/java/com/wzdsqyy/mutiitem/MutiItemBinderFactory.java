package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface MutiItemBinderFactory {
    @NonNull
    MutiItemBinder getMutiItemBinder(@LayoutRes int layoutRes,@NonNull ViewGroup parent);
}
