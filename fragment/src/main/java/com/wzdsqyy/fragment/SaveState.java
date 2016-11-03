package com.wzdsqyy.fragment;

import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2016/11/2.
 */

public interface SaveState {
    void onSaveInstanceState();
    void onPostResume();
    void commit(FragmentTransaction transaction);
    void commitNow(FragmentTransaction transaction);
}
