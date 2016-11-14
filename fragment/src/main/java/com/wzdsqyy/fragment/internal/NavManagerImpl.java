package com.wzdsqyy.fragment.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wzdsqyy.fragment.ContentPage;
import com.wzdsqyy.fragment.NavManager;
import com.wzdsqyy.fragment.SaveState;

/**
 * Created by Administrator on 2016/11/3.
 */
class NavManagerImpl extends BaseManager implements NavManager {
    private SaveState saveState;

    NavManagerImpl(@NonNull ContentPage contentPage, @NonNull SaveState saveState) {
        super(contentPage);
        this.saveState = saveState;
    }

    @Override
    public  <F extends Fragment> NavManager pushPage(F page,@Nullable String tag) {
        FragmentTransaction transaction = beginTransaction(true).replace(contentPage.getContentId(), page,tag).addToBackStack(tag);
        getSaveState().commit(transaction);
        return this;
    }
    @Override
    public NavManager popPage() {
        contentPage.getPageFragmentManager().popBackStack();
        return this;
    }
    @Override
    public NavManager clearNav() {
        if (contentPage.getPageFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = contentPage.getPageFragmentManager().getBackStackEntryAt(0);
            contentPage.getPageFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return this;
    }

    @Override
    public  <F extends Fragment> NavManager showPage(F page,@Nullable String tag) {
        return showPage(page, tag, false);
    }

    @Override
    public  <F extends Fragment> NavManager showPage(F page,@Nullable String tag, boolean isAnim) {
        beginTransaction(isAnim).replace(contentPage.getContentId(), page, tag).commitNow();
        return this;
    }

    @Override
    public int getBackStackCount() {
        return contentPage.getPageFragmentManager().getBackStackEntryCount();
    }


    @Override
    public SaveState getSaveState() {
        return saveState;
    }

    @Override
    public boolean onBackPressed() {
        if(getBackStackCount()>0){
            popPage();
            return true;
        }
        return false;
    }
}
