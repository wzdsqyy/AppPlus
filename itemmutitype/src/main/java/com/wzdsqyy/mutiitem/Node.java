package com.wzdsqyy.mutiitem;

import android.support.annotation.NonNull;

import com.wzdsqyy.mutiitem.internal.NodeHelper;

/**
 * Created by Administrator on 2016/11/18.
 */

public interface Node extends MutiItem{
    int getNodeType();//用于设置默认展开状态的时候用的；
    @NonNull NodeHelper getNodeHelper();
}
