package com.wzdsqyy.applib.utils.okhttp.progress;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Quinta on 2016/8/19.
 */
class ProgressInterceptor implements Interceptor {
    private ProgressListenerProvider progress;

    public ProgressInterceptor(ProgressListenerProvider progress) {
        this.progress = progress;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (progress == null) {
            return chain.proceed(chain.request());
        } else {
            Request request = chain.request();
            ProgressListener listener = progress.getProgressRequestListener(request);
            if (listener == null) {
                return chain.proceed(chain.request());
            }
            if ("POST".equals(request.method())) {
                ProgressListener requestListener = progress.getProgressRequestListener(request);
                ProgressRequestBody body = new ProgressRequestBody(request.body(), requestListener);
                request.newBuilder().post(body);
            }
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .body(new ProgressResponseBody(response.body(), progress.getProgressRequestListener(request)))
                    .build();
        }
    }
}
