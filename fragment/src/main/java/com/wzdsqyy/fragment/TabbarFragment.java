package com.wzdsqyy.fragment;

import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Qiuyy on 2016/8/26.
 */
public abstract class TabbarFragment extends BaseFragment implements Tabbar, TabbarManager {
    private int enter = 0;
    private int exit = 0;
    private int popEnter = 0;
    private int popExit = 0;
    private BaseFragment mShow;

    @Override
    public void showPage(BaseFragment page) {
        FragmentTransaction transaction = beginTransaction();
        List<Fragment> list = getTabbarFragmentManager().getFragments();
        Fragment fragment;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                fragment = list.get(i);
                if (fragment != page && fragment.isAdded()) {
                    transaction.hide(fragment);
                }
            }
        }
        mShow = page;
        transaction.show(page).commitNow();
    }

    private FragmentManager getTabbarFragmentManager() {
        return getChildFragmentManager();
    }

    private FragmentTransaction beginTransaction() {
        return getTabbarFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
    }

    @Override
    public void hidePage(BaseFragment page) {
        if (isVisible()) {
            beginTransaction().hide(page).commit();
        }
    }

    @Override
    public void addPage(BaseFragment page, String tag) {
        getTabbarFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE).add(getContentId(), page, tag).hide(page).commit();
    }

    @Override
    public void addPage(BaseFragment page) {
        addPage(page, page.getClass().getCanonicalName());
    }

    @Override
    public boolean onBackPressed() {
        if (mShow != null) {
            return mShow.onBackPressed();
        }
        return false;
    }


    @Override
    public TabbarManager setAnimations(@AnimRes int enter, @AnimRes int exit) {
        return setAnimations(enter, exit, 0, 0);
    }

    @Override
    public TabbarManager setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        this.enter = enter;
        this.exit = exit;
        this.popEnter = popEnter;
        this.popExit = popExit;
        return this;
    }

    private int getContentId() {
        int id = getTabContentId();
        return id;
    }

    @Override
    public abstract int getTabContentId();
}
