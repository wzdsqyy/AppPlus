package com.wzdsqyy.fragment;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Created by Qiuyy on 2016/8/26.
 */
public abstract class NavFragment extends BaseFragment implements Nav, NavManager {
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

    public NavManager getNavManager() {
        if(!isAdded()){
            throw new RuntimeException("必须在当Fragment关联了Avtivity 才可以调用");
        }
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        getNavFragmentManager().addOnBackStackChangedListener(getOnBackStackChangedListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getNavFragmentManager().removeOnBackStackChangedListener(getOnBackStackChangedListener());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNavManager().showPage(this.getRootFragment(), "Root_Nav_Page", false);
    }

    public NavManager pushPage(Fragment page, String tag) {
        beginTransaction(true).replace(getNavContentId(), page).addToBackStack(tag).commit();
        return this;
    }

    public NavManager popPage() {
        getNavFragmentManager().popBackStack();
        return this;
    }

    public NavManager clearNav() {
        if (getNavFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = getNavFragmentManager().getBackStackEntryAt(0);
            getNavFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return this;
    }

    public boolean isRootPage() {
        return getNavFragmentManager().getBackStackEntryCount() == 0;
    }

    public NavManager showPage(Fragment page, String tag) {
        return showPage(page, tag, true);
    }

    public NavManager showPage(Fragment page, String tag, boolean isAnim) {
        beginTransaction(isAnim).replace(getNavContentId(), page, tag).commit();
        if (isRootPage()) {
            getOnBackStackChangedListener().onBackStackChanged();
        }
        return this;
    }

    @Override
    public boolean onBackPressed() {
        if (getNavFragmentManager().getBackStackEntryCount() > 0) {
            popPage();
            return true;
        }
        return false;
    }

    @Override
    public FragmentManager getNavFragmentManager() {
        return getChildFragmentManager();
    }

    public int getBackStackCount() {
        return getNavFragmentManager().getBackStackEntryCount();
    }

    public NavManager setAnimations(@AnimRes int enter, @AnimRes int exit) {
        setAnimations(enter, exit, 0, 0);
        return this;
    }

    public NavManager setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        this.enter = enter;
        this.exit = exit;
        this.popEnter = popEnter;
        this.popExit = popExit;
        return this;
    }

    private FragmentTransaction beginTransaction(boolean isAnim) {
        if (isAnim) {
            return getNavFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
        } else {
            return getNavFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE);
        }
    }

    @Override
    public NavManager setOnNavBackStackListener(OnNavBackStackListener listener) {
        getOnBackStackChangedListener().setOnNavBackStackListener(listener);
        return this;
    }

    private static class OnBackStackChangedListenerInner implements FragmentManager.OnBackStackChangedListener {

        private OnNavBackStackListener onNavBackStackListener;
        private NavManager manager;

        public OnBackStackChangedListenerInner(NavManager manager) {
            this.manager = manager;
        }

        @Override
        public void onBackStackChanged() {
            if (manager != null) {
                if (onNavBackStackListener != null) {
                    onNavBackStackListener.onBackStackChanged(manager);
                }
            }
        }

        public void setOnNavBackStackListener(OnNavBackStackListener listener) {
            this.onNavBackStackListener = listener;
        }
    }
}
