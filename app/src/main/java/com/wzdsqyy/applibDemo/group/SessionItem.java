package com.wzdsqyy.applibDemo.group;

import com.wzdsqyy.mutiitem.internal.MutiItemHelper;
import com.wzdsqyy.mutiitem.internal.NodeHelper;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SessionItem extends NodeHelper{
    private String session = "组";
    private MutiItemHelper mutiItem=new MutiItemHelper();

    public SessionItem(int possion) {
        super(0);
        this.session = this.session + possion + "--";
    }

    public String getSession() {
        return session;
    }

    @Override
    public MutiItemHelper getMutiItem() {
        return mutiItem;
    }
}
