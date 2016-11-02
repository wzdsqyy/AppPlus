package com.wzdsqyy.fragment;

import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public final class PageManager implements TabbarManager, NavManager {
    private final boolean isTabbar;
    private final ContentPage contentPage;
    private int enter = 0;
    private int exit = 0;
    private int popEnter = 0;
    private int popExit = 0;
    private BaseFragment mShow;
    private FragmentTransaction transaction;
    private SaveState mSaveState;

    PageManager(boolean isTab, ContentPage contentPage) {
        isTabbar = isTab;
        this.contentPage = contentPage;
    }

    /**
     * @param page 父容器
     * @return 管理Tabbar的
     */
    public static TabbarManager attachTabbar(@NonNull ContentPage page) {
        PageManager manager = new PageManager(true, page);
        return manager;
    }


    /**
     * @param page 被管理的容器
     * @return 管理Tabbar的
     */
    public static NavManager attachNav(@NonNull ContentPage page) {
        PageManager manager = new PageManager(false, page);
        return manager;
    }

    @Override
    public TabbarManager showPage(int index) {
        List<Fragment> list = getFragmentManager().getFragments();
        if (list == null || index < 0 || index > list.size()) {
            throw new RuntimeException("error index");
        }
        FragmentTransaction transaction = beginTransaction();
        Fragment fragment;
        for (int i = 0; i < list.size(); i++) {
            fragment = list.get(i);
            if (index != i) {
                transaction.hide(fragment);
            } else {
                mShow = (BaseFragment) fragment;
                transaction.show(fragment);
            }
        }
        transaction.commitNow();
        return this;
    }

    @Override
    public SaveState getSaveState() {
        if(mSaveState==null){
            mSaveState=new SaveStateImpl();
        }
        return mSaveState;
    }

    private FragmentTransaction beginTransaction() {
        return getFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
    }

    @Override
    public TabbarManager addPage(BaseFragment page, String tag) {
        if (transaction == null) {
            transaction = beginTransaction();
            transaction.add(getTabContentId(), page, tag);
            return this;
        }
        transaction.add(getTabContentId(), page, tag).hide(page);
        return this;
    }

    @Override
    public TabbarManager addPage(BaseFragment page) {
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
        if (isTabbar) {
            if (mShow != null) {
                return mShow.onBackPressed();
            }
        } else {
            popPage();
        }
        return false;
    }

    public int getTabContentId() {
        return contentPage.getContentId();
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

    public NavManager pushPage(BaseFragment page,@Nullable String tag) {
        FragmentTransaction transaction = beginTransaction(true).replace(contentPage.getContentId(), page,tag).addToBackStack(tag);
        getSaveState().commit(transaction);
        return this;
    }

    public NavManager popPage() {
        getFragmentManager().popBackStack();
        return this;
    }

    public NavManager clearNav() {
        if (!isTabbar) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry entry = getFragmentManager().getBackStackEntryAt(0);
                getFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        return this;
    }

    @Override
    public NavManager showPage(BaseFragment page,@Nullable String tag) {
        return showPage(page, tag, false);
    }

    @Override
    public NavManager showPage(BaseFragment page,@Nullable String tag, boolean isAnim) {
        beginTransaction(isAnim).replace(getNavContentId(), page, tag).commitNow();
        return this;
    }

    @Override
    public int getBackStackCount() {
        return getFragmentManager().getBackStackEntryCount();
    }

    private FragmentManager getFragmentManager() {
        return contentPage.getPageFragmentManager();
    }

    public int getNavContentId() {
        return contentPage.getContentId();
    }

    private FragmentTransaction beginTransaction(boolean isAnim) {
        if (isAnim) {
            return getFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
        } else {
            return getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE);
        }
    }
}
