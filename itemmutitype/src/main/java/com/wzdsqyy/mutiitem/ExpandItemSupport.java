package com.wzdsqyy.mutiitem;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public interface ExpandItemSupport extends MutiItemSuport{
    void setDeleteList(List<MutiItemSuport> delete);
    List<MutiItemSuport> getDeleteList();
}
