package com.wzdsqyy.fragment.internal;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import com.wzdsqyy.fragment.ContentPage;
import com.wzdsqyy.fragment.NavManager;
import com.wzdsqyy.fragment.TabbarManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/1.
 */

public final class ManagerProvider{

    private ArrayList<Runnable> tasks;
    private boolean commit = true;

    private ManagerProvider() {
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
        return new TabbarManagerImpl(page, this);
    }


    /**
     * @param page 被管理的容器
     * @return 管理导航的
     */
    public NavManager createNavManager(@NonNull ContentPage page) {
        return new NavManagerImpl(page, this);
    }

    public ArrayList<Runnable> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    public void onSaveInstanceState() {
        commit = false;
    }

    public void onPostResume() {
        commit = true;
        if (tasks == null || tasks.size() == 0) {
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).run();
        }
        tasks.clear();
    }

    public void commit(FragmentTransaction transaction) {
        if (commit) {
            transaction.commit();
        } else {
            getTasks().add(new CommitAction(transaction, false));
        }
    }

    public void commitNow(FragmentTransaction transaction) {
        if (commit) {
            transaction.commitNow();
        } else {
            getTasks().add(new CommitAction(transaction, true));
        }
    }
}
