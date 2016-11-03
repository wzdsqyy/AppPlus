package com.wzdsqyy.fragment.internal;

import android.support.annotation.NonNull;

import com.wzdsqyy.fragment.ContentPage;
import com.wzdsqyy.fragment.NavManager;
import com.wzdsqyy.fragment.SaveState;
import com.wzdsqyy.fragment.TabbarManager;

/**
 * Created by Administrator on 2016/11/1.
 */

public final class ManagerProvider {
    SaveState mSaveState;

    public ManagerProvider() {
        mSaveState = new SaveStateImpl();
    }

    public static ManagerProvider newInstance() {
        ManagerProvider fragment = new ManagerProvider();
        return fragment;
    }

    /**
     * @param page 父容器
     * @return 管理Tabbar的
     */
    public TabbarManager createTabbarManager(@NonNull ContentPage page) {
        return new TabbarManagerImpl(page, mSaveState);
    }


    /**
     * @param page 被管理的容器
     * @return 管理导航的
     */
    public NavManager createNavManager(@NonNull ContentPage page) {
        return new NavManagerImpl(page, mSaveState);
    }

    public SaveState getSaveState() {
        return mSaveState;
    }
}
