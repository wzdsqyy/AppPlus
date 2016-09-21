package com.wzdsqyy.applibDemo.main;

import com.wzdsqyy.applibDemo.banner.ui.activity.BannerActivity;
import com.wzdsqyy.applibDemo.countdown.CountDownActivity;
import com.wzdsqyy.applibDemo.text.TextLineHelperActivity;
import com.wzdsqyy.applibDemo.updata.UpdataActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */

public class MainHelper {
    public static List<Bean> getActivityList(){
        List<Bean> list=new ArrayList<>();
        list.add(new Bean("倒计时", CountDownActivity.class));
        list.add(new Bean("Banner",BannerActivity.class));
        list.add(new Bean("文字的展开与收缩",TextLineHelperActivity.class));
        list.add(new Bean("升级",UpdataActivity.class));
        return list;
    }
}
