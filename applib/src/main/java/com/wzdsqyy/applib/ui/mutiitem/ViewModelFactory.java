package com.wzdsqyy.applib.ui.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public interface ViewModelFactory {
    @NonNull
    MutiItemHolder getMutiItemHolder(@LayoutRes int layoutRes);
}
