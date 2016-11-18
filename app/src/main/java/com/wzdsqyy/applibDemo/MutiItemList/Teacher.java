package com.wzdsqyy.applibDemo.MutiItemList;

import com.wzdsqyy.mutiitem.MutiItem;
import com.wzdsqyy.mutiitem.internal.MutiItemHelper;

/**
 * Created by Administrator on 2016/10/12.
 */

public class Teacher implements MutiItem {
    private MutiItemHelper helper=new MutiItemHelper();
    @Override
    public MutiItemHelper getMutiItem() {
        return helper;
    }
}
