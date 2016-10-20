package com.wzdsqyy.utils.okhttp.progress;

import okhttp3.Request;

/**
 * Created by Quinta on 2016/8/19.
 */
public interface ProgressListenerProvider {
    ProgressListener getProgressRequestListener(Request request);
}
