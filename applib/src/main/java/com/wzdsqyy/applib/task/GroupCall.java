package com.wzdsqyy.applib.task;

import java.util.concurrent.Callable;

/**
 * Created by Qiuyy on 2016/9/13.
 */
public interface GroupCall<V>{
    GroupCall<V> addCallable(Callable<V> callable);
    GroupCall<V> setGroupListener(GroupListener listener);
}
