package com.wzdsqyy.weiman.ui.comics.presenter;

/**
 * Created by Administrator on 2016/11/11.
 */

public interface CallBack<T> {
   void onLoadError(Throwable ex);
    void onSuccess(T value);
}
