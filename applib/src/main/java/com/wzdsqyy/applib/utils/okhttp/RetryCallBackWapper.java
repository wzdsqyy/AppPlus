package com.wzdsqyy.applib.utils.okhttp;

import android.support.annotation.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/11.
 */

public class RetryCallBackWapper implements Callback {
    private static final int DEFAULT_RETRY_TIME = 3;
    private Callback callback;
    private int times = 0;
    private int mMaxTimes;
    private boolean retry = true;

    public RetryCallBackWapper(@NonNull Callback callback) {
        this(DEFAULT_RETRY_TIME, callback);
    }

    public RetryCallBackWapper(int mMaxTimes,@NonNull Callback callback) {
        this.mMaxTimes = mMaxTimes;
        this.callback = callback;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        while (retry) {
            if(times >= mMaxTimes){
                retry=false;
                continue;
            }
            if (e instanceof MalformedURLException) {
                retry = false;
            } else if (e instanceof NoRouteToHostException) {
                retry = false;
            } else if (e instanceof PortUnreachableException) {
                retry = false;
            } else if (e instanceof ProtocolException) {
                retry = false;
            } else if (e instanceof FileNotFoundException) {
                retry = false;
            } else if (e instanceof UnknownHostException) {
                retry = false;
            } else {
                times++;
                call.enqueue(this);
            }
        }
        callback.onFailure(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        callback.onResponse(call, response);
    }
}
