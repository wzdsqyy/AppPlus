package com.wzdsqyy.mutiitem;

import android.support.annotation.NonNull;

import com.wzdsqyy.mutiitem.internal.NodeHelper;

/**
 * Created by Administrator on 2016/11/18.
 */

public interface Node extends MutiItem{
    @NonNull NodeHelper getNodeHelper();
}
