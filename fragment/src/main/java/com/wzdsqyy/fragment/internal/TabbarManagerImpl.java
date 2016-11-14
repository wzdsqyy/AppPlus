package com.wzdsqyy.fragment.internal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.wzdsqyy.fragment.ContentPage;
import com.wzdsqyy.fragment.SaveState;
import com.wzdsqyy.fragment.TabbarManager;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
class TabbarManagerImpl extends BaseManager implements TabbarManager {
    private SaveState saveState;
    private FragmentTransaction transaction;

    TabbarManagerImpl(ContentPage contentPage, SaveState saveState) {
        super(contentPage);
        this.saveState = saveState;
    }

    @Override
    public TabbarManager showPage(int index) {
        return showPage(index, false);
    }

    @Override
    public TabbarManager showPage(int index, boolean anim) {
        List<Fragment> list = contentPage.getPageFragmentManager().getFragments();
        if (list == null || index < 0 || index > list.size()) {
            throw new RuntimeException("error index");
        }
        FragmentTransaction transaction = beginTransaction(anim);
        Fragment fragment;
        for (int i = 0; i < list.size(); i++) {
            fragment = list.get(i);
            if (index != i) {
                transaction.hide(fragment);
            } else {
                transaction.show(fragment);
            }
        }
        getSaveState().commitNow(transaction);
        return this;
    }

    @Override
    public SaveState getSaveState() {
        return saveState;
    }

    @Override
    public  <F extends Fragment> TabbarManager addPage(F page, String tag) {
        if (transaction == null) {
            transaction = beginTransaction(false);
            transaction.add(contentPage.getContentId(), page, tag);
            return this;
        }
        transaction.add(contentPage.getContentId(), page, tag).hide(page);
        return this;
    }

    @Override
    public   <F extends Fragment> TabbarManager addPage(F page) {
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
        return false;
    }
}
