package com.wzdsqyy.applibDemo.main;

import com.wzdsqyy.applibDemo.ImageSelector.ImageSelectorActivity;
import com.wzdsqyy.applibDemo.banner.ui.activity.BannerActivity;
import com.wzdsqyy.applibDemo.blur.BlurActivity;
import com.wzdsqyy.applibDemo.countdown.CountDownActivity;
import com.wzdsqyy.applibDemo.recreate.ReCreateActivity;
import com.wzdsqyy.applibDemo.shapview.RoundAngleActivity;
import com.wzdsqyy.applibDemo.sticky.StickyActivity;
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
        list.add(new Bean("图片选择",ImageSelectorActivity.class));
        list.add(new Bean("任意View圆角",RoundAngleActivity.class));
        list.add(new Bean("创建Activity",ReCreateActivity.class));
        list.add(new Bean("Blur",BlurActivity.class));
        return list;
    }
}
