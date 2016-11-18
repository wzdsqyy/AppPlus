package com.wzdsqyy.applibDemo.group;

import com.wzdsqyy.mutiitem.internal.MutiItemHelper;
import com.wzdsqyy.mutiitem.internal.NodeHelper;

/**
 * Created by Administrator on 2016/11/16.
 */

public class Item extends NodeHelper{
    private String item = "组员_";
    private MutiItemHelper mutiItem=new MutiItemHelper();
    private int section;

    public Item(int possion) {
        super(1);
        this.item = this.item + possion+"--";
    }

    public String getItem() {
        return item+section+"_";
    }

    @Override
    public MutiItemHelper getMutiItem() {
        return mutiItem;
    }

    public void setSection(int section) {
        this.section = section;
    }
}
