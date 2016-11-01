package com.wzdsqyy.fragment;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Created by Qiuyy on 2016/8/26.
 */
abstract class NavFragment extends BaseFragment implements NavManager,ContentPage{
    private int enter = 0;
    private int exit = 0;
    private int popEnter = 0;
    private int popExit = 0;
    private OnBackStackChangedListenerInner onBackStackChangedListener;

    private OnBackStackChangedListenerInner getOnBackStackChangedListener() {
        if (onBackStackChangedListener == null) {
            onBackStackChangedListener = new OnBackStackChangedListenerInner(this);
        }
        return onBackStackChangedListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        getPageFragmentManager().addOnBackStackChangedListener(getOnBackStackChangedListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPageFragmentManager().removeOnBackStackChangedListener(getOnBackStackChangedListener());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNavManager().showPage(getRootFragment(), "Root_Nav_Page", false);
    }

    public NavManager pushPage(Fragment page, String tag) {
        beginTransaction(true).replace(getContentId(), page).addToBackStack(tag).commit();
        return this;
    }

    public NavManager popPage() {
        getPageFragmentManager().popBackStack();
        return this;
    }

    public NavManager clearNav() {
        if (getPageFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = getPageFragmentManager().getBackStackEntryAt(0);
            getPageFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return this;
    }

    public boolean isRootPage() {
        return getPageFragmentManager().getBackStackEntryCount() == 0;
    }

    public NavManager showPage(Fragment page, String tag) {
        return showPage(page, tag, true);
    }

    public NavManager showPage(Fragment page, String tag, boolean isAnim) {
        beginTransaction(isAnim).replace(getContentId(), page, tag).commit();
        if (isRootPage()) {
            getOnBackStackChangedListener().onBackStackChanged();
        }
        return this;
    }

    @Override
    public boolean onBackPressed() {
        if (getPageFragmentManager().getBackStackEntryCount() > 0) {
            popPage();
            return true;
        }
        return false;
    }

    @Override
    public FragmentManager getPageFragmentManager() {
        return getChildFragmentManager();
    }

    public int getBackStackCount() {
        return getPageFragmentManager().getBackStackEntryCount();
    }

    public void setAnimations(@AnimRes int enter, @AnimRes int exit) {
        setAnimations(enter, exit, 0, 0);
    }

    public void setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        this.enter = enter;
        this.exit = exit;
        this.popEnter = popEnter;
        this.popExit = popExit;
    }

    private FragmentTransaction beginTransaction(boolean isAnim) {
        if (isAnim) {
            return getPageFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
        } else {
            return getPageFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE);
        }
    }

    public NavManager setOnNavBackStackListener(OnNavBackStackListener listener) {
        getOnBackStackChangedListener().setOnNavBackStackListener(listener);
        return this;
    }

    @NonNull
    public abstract Fragment getRootFragment();
}
