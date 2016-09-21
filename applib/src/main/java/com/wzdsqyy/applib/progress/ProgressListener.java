package com.wzdsqyy.applib.progress;

/**
 * Created by Quinta on 2016/8/19.
 */
public interface ProgressListener {
    /**
     * @param progress
     * @param total
     */
    boolean onProgress(long progress, long total, boolean done);
}
