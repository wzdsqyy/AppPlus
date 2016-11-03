package com.wzdsqyy.fragment.internal;

import android.support.annotation.AnimRes;
import android.support.v4.app.FragmentTransaction;

import com.wzdsqyy.fragment.ContentPage;
import com.wzdsqyy.fragment.Manager;
import com.wzdsqyy.fragment.SaveState;

/**
 * Created by Administrator on 2016/11/3.
 */
 abstract class BaseManager implements Manager {
    final ContentPage contentPage;
    private int enter = 0;
    private int exit = 0;
    private int popEnter = 0;
    private int popExit = 0;
    private FragmentTransaction transaction;
    private SaveState mSaveState;

    public BaseManager(ContentPage contentPage) {
        this.contentPage = contentPage;
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

    FragmentTransaction beginTransaction(boolean isAnim) {
        if (isAnim) {
            return contentPage.getPageFragmentManager().beginTransaction().setCustomAnimations(enter, exit, popEnter, popExit);
        } else {
            return contentPage.getPageFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE);
        }
    }
}
