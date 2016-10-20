package com.wzdsqyy.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public interface Nav {
    /**
     * @return 内容显示空间的Id
     */
    int getNavContentId();

    Fragment getRootFragment();
}
