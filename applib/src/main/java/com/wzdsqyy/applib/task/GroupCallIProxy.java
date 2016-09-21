package com.wzdsqyy.applib.task;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Qiuyy on 2016/9/13.
 */
class GroupCallIProxy<V> implements GroupCall<V> {
    private ArrayList<Callable<V>> calls;
    private GroupListener listener;

    public ArrayList<Callable<V>> getCalls() {
        if(calls==null){
            calls=new ArrayList<>();
        }
        return calls;
    }

    @Override
    public GroupCall addCallable(Callable<V> callable) {
        getCalls().add(callable);
        return this;
    }

    @Override
    public GroupCall setGroupListener(GroupListener listener) {
        this.listener=listener;
        return this;
    }
}
