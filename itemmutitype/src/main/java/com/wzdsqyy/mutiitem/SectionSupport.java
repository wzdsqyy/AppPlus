package com.wzdsqyy.mutiitem;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public interface SectionSupport extends MutiItemSuport {
    List<MutiItemSuport> getSectionItems();//null，表示该组无分组数据

    void setSectionItems(List<MutiItemSuport> list);
}
