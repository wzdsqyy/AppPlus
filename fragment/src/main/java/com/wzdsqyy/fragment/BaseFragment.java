package com.wzdsqyy.fragment;


import android.support.v4.app.Fragment;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public class BaseFragment extends Fragment {
    private OnUserVisibleHintListener userVisibleHintListener;

    public BaseFragment setUserVisibleHintListener(OnUserVisibleHintListener userVisibleHintListener) {
        this.userVisibleHintListener = userVisibleHintListener;
        onResume();
        return this;
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (userVisibleHintListener == null) {
            return;
        }
        userVisibleHintListener.onUserVisibleHint(isVisibleToUser);
    }
}
