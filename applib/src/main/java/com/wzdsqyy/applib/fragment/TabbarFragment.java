package com.wzdsqyy.applib.fragment;

import android.support.annotation.AnimRes;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Qiuyy on 2016/8/26.
 */
public abstract class TabbarFragment extends BaseFragment implements Tabbar,TabbarManager {
    private BaseFragment mShow;
    private int enter=0;
    private int exit=0;
    private int popEnter=0;
    private int popExit=0;

    public TabbarManager getTabbarManager() {
        return this;
    }

    @Override
    public void showPage(BaseFragment page) {
        if(page!=mShow){
            FragmentTransaction transaction = beginTransaction();
            if (mShow != null) {
                transaction.hide(mShow);
            }
            transaction.show(page).commit();
            mShow = page;
        }
    }

    private FragmentTransaction beginTransaction(){
        return getFragmentManager().beginTransaction().setCustomAnimations(enter,exit,popEnter,popExit);
    }

    @Override
    public void hidePage(BaseFragment page) {
        if(isVisible()){
            beginTransaction().hide(page).commit();
        }
    }

    @Override
    public void addPage(BaseFragment page, String tag) {
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_NONE).add(getTabContentId(), page, tag).hide(page).commit();
    }

    @Override
    public void addPage(BaseFragment page) {
        addPage(page,null);
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
        return setAnimations(enter,exit,0,0);
    }

    @Override
    public TabbarManager setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        this.enter=enter;
        this.exit=exit;
        this.popEnter=popEnter;
        this.popExit=popExit;
        return this;
    }

    @Override
    public abstract int getTabContentId();
}
