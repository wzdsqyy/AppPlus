package com.wzdsqyy.ageratest.mvp.view;

/**
 * Created by Administrator on 2016/10/17.
 */

public interface LoadingView<T> {
    void showLoading(boolean canCancel);

    void closeLoading(boolean success, T data);
}
