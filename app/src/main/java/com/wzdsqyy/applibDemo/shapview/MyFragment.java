package com.wzdsqyy.applibDemo.shapview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/9/22.
 */

public class MyFragment extends Fragment implements View.OnAttachStateChangeListener,View.OnSystemUiVisibilityChangeListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        v.setVisibility(View.GONE);
    }


    @Override
    public void onViewDetachedFromWindow(View v) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {


    }

}
