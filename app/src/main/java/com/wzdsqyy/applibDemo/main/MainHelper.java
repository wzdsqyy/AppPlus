package com.wzdsqyy.applibDemo.main;

import android.widget.ExpandableListView;

import com.wzdsqyy.applibDemo.ExpandActivity;
import com.wzdsqyy.applibDemo.ImageSelector.ImageSelectorActivity;
import com.wzdsqyy.applibDemo.MutiItemList.MutiTypeActivity;
import com.wzdsqyy.applibDemo.MutiItemList.PullRefershActivity;
import com.wzdsqyy.applibDemo.MutiItemList.ScrollingActivity;
import com.wzdsqyy.applibDemo.TabTestActivity;
import com.wzdsqyy.applibDemo.banner.ui.activity.BannerActivity;
import com.wzdsqyy.applibDemo.blur.BlurActivity;
import com.wzdsqyy.applibDemo.countdown.CountDownActivity;
import com.wzdsqyy.applibDemo.dialog.DialogActivity;
import com.wzdsqyy.applibDemo.fragment.OneActivity;
import com.wzdsqyy.applibDemo.loop.LooperActivity;
import com.wzdsqyy.applibDemo.lrulist.LruListActivity;
import com.wzdsqyy.applibDemo.recreate.ReCreateActivity;
import com.wzdsqyy.applibDemo.shapview.RoundAngleActivity;
import com.wzdsqyy.applibDemo.swapadapter.SwapAdapterActivity;
import com.wzdsqyy.applibDemo.text.TextLineHelperActivity;
import com.wzdsqyy.applibDemo.updata.UpdataActivity;
import com.wzdsqyy.applibDemo.viewpager.ListViewActivity;
import com.wzdsqyy.applibDemo.viewpager.NestedListViewActivity;
import com.wzdsqyy.applibDemo.viewpager.NestedTwoListViewActivity;
import com.wzdsqyy.applibDemo.viewpager.NestedViewPagerActivity;
import com.wzdsqyy.applibDemo.viewpager.ViewPagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */

public class MainHelper {
    public static List<Bean> getActivityList() {
        List<Bean> list = new ArrayList<>();
        list.add(new Bean("倒计时", CountDownActivity.class));
        list.add(new Bean("Banner", BannerActivity.class));
        list.add(new Bean("文字的展开与收缩", TextLineHelperActivity.class));
        list.add(new Bean("升级", UpdataActivity.class));
        list.add(new Bean("图片选择", ImageSelectorActivity.class));
        list.add(new Bean("任意View圆角", RoundAngleActivity.class));
        list.add(new Bean("Dialog", DialogActivity.class));
        list.add(new Bean("创建Activity", ReCreateActivity.class));
        list.add(new Bean("Blur", BlurActivity.class));
        list.add(new Bean("pull", PullRefershActivity.class));
        list.add(new Bean("单Activity应用", OneActivity.class));
        list.add(new Bean("无限滚动", SwapAdapterActivity.class));
        list.add(new Bean("LruList", LruListActivity.class));
        list.add(new Bean("Mutitype", MutiTypeActivity.class));
        list.add(new Bean("ViewPager", ViewPagerActivity.class));
        list.add(new Bean("LooperViewPager", LooperActivity.class));
        list.add(new Bean("NestedViewpager", NestedViewPagerActivity.class));
        list.add(new Bean("NestedListInPager", NestedListViewActivity.class));
        list.add(new Bean("NestedPagerInList", ListViewActivity.class));
        list.add(new Bean("NestedTwoListViewActivity", NestedTwoListViewActivity.class));
        list.add(new Bean("PullRefershActivity", PullRefershActivity.class));
        list.add(new Bean("ScrollingActivity", ScrollingActivity.class));
        list.add(new Bean("可展开的视图", ExpandActivity.class));
        list.add(new Bean("Tab", TabTestActivity.class));
        return list;
    }
}
