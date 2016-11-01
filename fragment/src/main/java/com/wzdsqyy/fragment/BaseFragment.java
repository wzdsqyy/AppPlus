package com.wzdsqyy.fragment;


import android.support.v4.app.Fragment;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public class BaseFragment extends Fragment {

    private OnUserVisibleHintListener userVisibleHintListener;

    public BaseFragment setUserVisibleHintListener(OnUserVisibleHintListener userVisibleHintListener) {
        this.userVisibleHintListener = userVisibleHintListener;
        return this;
    }

    public boolean onBackPressed() {
        return false;
    }

    public NavManager getNavManager() {
        if (!isAdded()) {
            throw new RuntimeException("必须在添加之后才可以调用");
        }
        if (this instanceof NavManager) {
            return (NavManager) this;
        } else {
            Fragment parent = getParentFragment();
            if (parent != null && parent instanceof NavFragment) {
                return ((NavFragment) parent).getNavManager();
            } else {
                throw new RuntimeException("此Fragment的父Fragment不是NavFragment");
            }
        }
    }

    public TabbarManager getTabbarManager() {
        if (!isAdded()) {
            throw new RuntimeException("必须在添加之后才可以调用");
        }
        if (this instanceof NavManager) {
            return (TabbarManager) this;
        } else {
            Fragment parent = getParentFragment();
            if (parent != null && parent instanceof NavFragment) {
                return ((TabbarFragment) parent).getTabbarManager();
            } else {
                throw new RuntimeException("此Fragment的父Fragment不是TabbarFragment");
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(userVisibleHintListener==null){
            return;
        }
        userVisibleHintListener.onUserVisibleHint(isVisibleToUser);
    }
}
