package com.wzdsqyy.applibDemo.group;

import android.support.annotation.LayoutRes;

import com.wzdsqyy.mutiitem.MutiItemSuport;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SessionItem implements MutiItemSuport {
    private int layoutRes = -1;
    private String session = "组";

    public SessionItem(int possion) {
        this.session = this.session + possion;
    }

    public String getSession() {
        return session;
    }

    @Override
    public int getMutiItemViewType() {
        return layoutRes;
    }

    @Override
    public void setMutiItemViewType(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }
}
