package com.wzdsqyy.fragment;

import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Qiuyy on 2016/8/26.
 */
abstract class TabbarFragment extends BaseFragment implements TabbarManager,ContentPage {
    private int enter = 0;
    private int exit = 0;
    private int popEnter = 0;
    private int popExit = 0;
    private BaseFragment mShow;
    private FragmentTransaction transaction;

    @Override
    public TabbarManager showPage(BaseFragment page) {
        FragmentTransaction transaction = beginTransaction();
        List<Fragment> list = getPageFragmentManager().getFragments();
        Fragment fragment;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                fragment = list.get(i);
                if (fragment != page && fragment.isAdded() && fragment.getId() == getContentId()) {
                    transaction.hide(fragment);
                }
            }
        }
        mShow = page;
        transaction.show(page).commitNow();
        return this;
    }

    public FragmentManager getPageFragmentManager() {
        return getChildFragmentManager();
    }

    private FragmentTransaction beginTransaction() {
        return getPageFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
    }

    @Override
    public TabbarManager hidePage(BaseFragment page) {
        if (isVisible()) {
            beginTransaction().hide(page).commit();
        }
        return this;
    }

    @Override
    public FragmentTransaction addPage(BaseFragment page, String tag) {
        if (transaction == null) {
            transaction = beginTransaction();
            transaction.add(getContentId(), page, tag);
            return transaction;
        }
        return transaction.add(getContentId(), page, tag).hide(page);
    }

    @Override
    public FragmentTransaction addPage(BaseFragment page) {
        return addPage(page, page.getClass().getCanonicalName());
    }

    @Override
    public TabbarManager commit() {
        if (transaction != null) {
            transaction.commitNow();
            transaction = null;
        }
        return this;
    }

    @Override
    public boolean onBackPressed() {
        if (mShow != null) {
            return mShow.onBackPressed();
        }
        return false;
    }


    @Override
    public void setAnimations(@AnimRes int enter, @AnimRes int exit) {
        setAnimations(enter, exit, 0, 0);
    }

    @Override
    public void setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        this.enter = enter;
        this.exit = exit;
        this.popEnter = popEnter;
        this.popExit = popExit;
    }
}
