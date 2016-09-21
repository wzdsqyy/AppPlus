package com.wzdsqyy.applib.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.Future;

/**
 * Created by Qiuyy on 2016/9/12.
 */
public interface FutureListener<V,H>{
    /**
     *在UI线程回调
     * @param who 用户需要持有的引用，是弱引用，为null时将不会回调该方法。
     * @param future 谁的回调
     * @param result 后台运行结果
     * @param e 为null为正常运行结束
     */
    void onTaskComplete(@Nullable H who, @NonNull Future<V> future, @Nullable V result, @Nullable Throwable e);
}
