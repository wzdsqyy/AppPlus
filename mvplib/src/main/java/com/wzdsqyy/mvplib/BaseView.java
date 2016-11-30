package com.wzdsqyy.mvplib;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 子类继承处理Ui相关的逻辑
 */

public class BaseView {
    private View container;

    public BaseView(@NonNull View container) {
        this.container = container;
        ButterKnife.bind(this, container);
    }

    public BaseView() {
    }

    public BaseView setContainer(@NonNull View container) {
        this.container = container;
        ButterKnife.bind(this, container);
        return this;
    }

    public View getContainer() {
        return container;
    }

    public Context getContext() {
        if(container==null){
            return null;
        }
        return container.getContext();
    }

    public BaseView(@LayoutRes int layout, @NonNull Context context) {
        container = LayoutInflater.from(context).inflate(layout, null);
        ButterKnife.bind(this, container);
    }

    public BaseView(@LayoutRes int layout, @NonNull LayoutInflater inflater, ViewGroup parent) {
        container = inflater.inflate(layout, parent, false);
        ButterKnife.bind(this, container);
    }
}
