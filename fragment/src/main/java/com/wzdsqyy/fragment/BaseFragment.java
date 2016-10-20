package com.wzdsqyy.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public class BaseFragment extends Fragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        if (savedState != null) {
            boolean hidden = savedState.getBoolean(STATE_SAVE_IS_HIDDEN);
            if (isHidden() != hidden && isAdded()) {
                if (hidden) {
                    getFragmentManager().beginTransaction().hide(this).commit();
                } else {
                    getFragmentManager().beginTransaction().show(this).commit();
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public boolean onBackPressed(){
        return false;
    }

    public NavManager getNavManager() {
        Fragment parent = getParentFragment();
        if(parent!=null&&parent instanceof NavFragment){
            return ((NavFragment) parent).getNavManager();
        }else{
            throw new RuntimeException("此Fragment的父Fragment不是NavFragment");
        }
    }

    public TabbarManager getTabbarManager(){
        Fragment parent = getParentFragment();
        if(parent!=null&&parent instanceof TabbarFragment){
            return ((TabbarFragment) parent).getTabbarManager();
        }else{
            throw new RuntimeException("此Fragment的父Fragment不是NavFragment");
        }
    }
}
