package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public interface ViewModelFactory {
    @NonNull
    MutiItemBinder getMutiItemHolder(@LayoutRes int layoutRes);
}
