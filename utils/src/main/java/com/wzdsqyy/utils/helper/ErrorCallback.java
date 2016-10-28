package com.wzdsqyy.utils.helper;

/**
 * Created by Administrator on 2016/10/28.
 */

public interface ErrorCallback<T> extends Callback<T>{
    void onFailure(Throwable e);
}
