package com.wzdsqyy.fragment;

import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public final class PageManager implements TabbarManager, NavManager {
    private final boolean isTabbar;
    private final BaseFragment attach;
    private final ContentPage contentPage;
    private int enter = 0;
    private int exit = 0;
    private int popEnter = 0;
    private int popExit = 0;
    private BaseFragment mShow;
    private FragmentTransaction transaction;

    PageManager(boolean isTab, BaseFragment tabbar, ContentPage contentPage) {
        isTabbar = isTab;
        this.attach = tabbar;
        this.contentPage = contentPage;
    }

    private static <F extends BaseFragment> PageManager attach(@NonNull F tabbar, @NonNull ContentPage page, @IdRes int contentId, @NonNull String tag, @NonNull FragmentManager manager, boolean isTab) {
        PageManager fragment = new PageManager(isTab, tabbar, page);
        manager.beginTransaction().replace(contentId, tabbar, tag).commitNow();
        return fragment;
    }

    /**
     * @param tabbar
     * @param page      管理Fragment中的那个id的接口
     * @param contentId
     * @param tag
     * @param manager
     * @param <F>
     * @return 管理Tabbar的
     */
    public static <F extends BaseFragment> TabbarManager attachTabbar(@NonNull F tabbar, @NonNull ContentPage page, @IdRes int contentId, @NonNull String tag, @NonNull FragmentManager manager) {
        return attach(tabbar, page, contentId, tag, manager, true);
    }

    /**
     * @param nav
     * @param page      管理Fragment中的那个id的接口
     * @param contentId
     * @param tag
     * @param manager
     * @param <F>
     * @return 管理导航的
     */
    public static <F extends BaseFragment> NavManager attachNav(@NonNull F nav, @NonNull ContentPage page, @IdRes int contentId, @NonNull String tag, @NonNull FragmentManager manager) {
        return attach(nav, page, contentId, tag, manager, false);
    }

    @Override
    public TabbarManager showPage(BaseFragment page) {
        FragmentTransaction transaction = beginTransaction();
        List<Fragment> list = getPageFragmentManager().getFragments();
        Fragment fragment;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                fragment = list.get(i);
                if (fragment != page && fragment.isAdded() && fragment.getId() == getTabContentId()) {
                    transaction.hide(fragment);
                }
            }
        }
        mShow = page;
        transaction.show(page).commitNow();
        return this;
    }

    private FragmentTransaction beginTransaction() {
        return getPageFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
    }

    @Override
    public TabbarManager hidePage(BaseFragment page) {
        if (isTabbar && attach.isVisible()) {
            beginTransaction().hide(page).commit();
        }
        return this;
    }

    @Override
    public FragmentTransaction addPage(BaseFragment page, String tag) {
        if (transaction == null) {
            transaction = beginTransaction();
            transaction.add(getTabContentId(), page, tag);
            return transaction;
        }
        return transaction.add(getTabContentId(), page, tag).hide(page);
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

    public NavManager pushPage(Fragment page, String tag) {
        if (!isTabbar) {
            beginTransaction(true).replace(this.contentPage.getContentId(), page).addToBackStack(tag).commit();
        }
        return this;
    }

    public NavManager popPage() {
        getPageFragmentManager().popBackStack();
        return this;
    }

    public NavManager clearNav() {
        if (!isTabbar) {
            if (getPageFragmentManager().getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry entry = getPageFragmentManager().getBackStackEntryAt(0);
                getPageFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        return this;
    }

    @Override
    public NavManager showPage(Fragment page, String tag) {
        return showPage(page, tag, true);
    }

    @Override
    public NavManager showPage(Fragment page, String tag, boolean isAnim) {
        beginTransaction(isAnim).replace(getNavContentId(), page, tag).commit();
        return this;
    }

    @Override
    public int getBackStackCount() {
        return getPageFragmentManager().getBackStackEntryCount();
    }

    @Override
    public FragmentManager getPageFragmentManager() {
        return getPageFragmentManager();
    }

    public int getNavContentId() {
        return contentPage.getContentId();
    }

    private FragmentTransaction beginTransaction(boolean isAnim) {
        if (isAnim) {
            return getPageFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
        } else {
            return getPageFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE);
        }
    }
}
