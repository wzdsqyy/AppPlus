package com.wzdsqyy.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;

/**
 * Created by Administrator on 2016/11/1.
 */

public interface ContentPage {
    /**
     * @return Optional identifier of the container this fragment is
     * to be placed in.  If 0, it will not be placed in a container.
     */
    @IdRes
    int getContentId();

    boolean onUserPressedBack();

    FragmentManager getPageFragmentManager();
}
