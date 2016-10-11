package com.wzdsqyy.applib.utils.okhttp.progress;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Quinta on 2016/8/19.
 */
class ProgressCallback implements Callback {
    private ProgressListener listener;
    private Callback callback;

    public ProgressCallback(ProgressListener listener, Callback callback) {
        this.listener = listener;
        this.callback = callback;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        callback.onFailure(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        response = response.newBuilder()
                .body(new ProgressResponseBody(response.body(), listener))
                .build();
        callback.onResponse(call, response);
    }
}
