package com.wzdsqyy.applib.task;

import android.support.annotation.Nullable;

/**
 * Created by Qiuyy on 2016/9/12.
 */
public interface GroupListener{
    /**
     * 在UI线程回调
     * @param e 为null为正常运行结束
     */
    void onTaskComplete(@Nullable Throwable e);
}
