package com.wzdsqyy.utils.okhttp.progress;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Quinta on 2016/8/19.
 */
public class ProgressHelper {

    public static RequestBody upload(RequestBody requestBody, ProgressListener listener) {
        return new ProgressRequestBody(requestBody, listener);
    }

    public static Response download(OkHttpClient client, Request request, ProgressListener listener) throws IOException {
        return download(client.newCall(request), listener);
    }

    /**
     * 只有从网络下载时才回调监听
     *
     * @param client
     * @param progress
     * @return
     */
    public static OkHttpClient downloadListenNet(OkHttpClient client, ProgressListenerProvider progress) {
        return client.newBuilder()
                .addNetworkInterceptor(new ProgressInterceptor(progress))
                .build();
    }

    public static void downloadListenNet(OkHttpClient client, Request request, Callback callback, ProgressListenerProvider progress) {
        downloadListenNet(client, progress)
                .newCall(request)
                .enqueue(callback);
    }

    public static void download(OkHttpClient client, Request request, ProgressListener listener, Callback callback) {
        download(client.newCall(request), listener, callback);
    }

    public static RequestBody upload(Call call, ProgressListener listener) {
        return new ProgressRequestBody(call.request().body(), listener);
    }

    public static void download(Call call, ProgressListener listener, Callback callback) {
        if (listener != null) {
            call.enqueue(new ProgressCallbackWapper(listener, callback));
        } else {
            call.enqueue(callback);
        }
    }

    public static Response download(Call call, ProgressListener listener) throws IOException {
        Response response = call.execute();
        response = response.newBuilder()
                .body(new ProgressResponseBody(response.body(), listener))
                .build();
        return response;
    }
}
