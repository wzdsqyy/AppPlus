package com.wzdsqyy.fragment.internal;

import android.support.annotation.NonNull;

import com.wzdsqyy.fragment.ContentPage;
import com.wzdsqyy.fragment.Manager;
import com.wzdsqyy.fragment.NavManager;
import com.wzdsqyy.fragment.SaveState;
import com.wzdsqyy.fragment.TabbarManager;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/1.
 */

public final class ManagerProvider {
    HashMap<ContentPage, Manager> managers;
    SaveState mSaveState;

    public ManagerProvider() {
        mSaveState = new SaveStateImpl();
        managers = new HashMap();
    }

    public static ManagerProvider newInstance() {
        ManagerProvider fragment = new ManagerProvider();
        return fragment;
    }

    /**
     * @param page 父容器
     * @return 管理Tabbar的
     */
    public TabbarManager getTabbar(@NonNull ContentPage page) {
        TabbarManager manager = (TabbarManager) managers.get(page);
        if (manager == null) {
            manager = new TabbarManagerImpl(page, mSaveState);
            managers.put(page, manager);
        }
        return manager;
    }


    /**
     * @param page 被管理的容器
     * @return 管理Tabbar的
     */
    public NavManager getNav(@NonNull ContentPage page) {
        NavManager manager = (NavManager) managers.get(page);
        if (manager == null) {
            manager = new NavManagerImpl(page, mSaveState);
            managers.put(page, manager);
        }
        return manager;
    }

    public SaveState getSaveState() {
        return mSaveState;
    }
}
