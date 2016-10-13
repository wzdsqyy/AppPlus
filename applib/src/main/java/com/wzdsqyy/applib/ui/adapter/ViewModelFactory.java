package com.wzdsqyy.applib.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public interface ViewModelFactory {
    @NonNull
    MutiItemBinder getMutiItemHolder(@LayoutRes int layoutRes);
}
