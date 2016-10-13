package com.wzdsqyy.applibDemo.banner.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wzdsqyy.applib.ui.banner.BGABanner;
import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.banner.engine.Engine;
import com.wzdsqyy.applibDemo.banner.model.BannerModel;
import com.wzdsqyy.applibDemo.main.App;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/7/21 下午8:26
 * 描述:
 */
public class ListViewDemoActivity extends Activity {
    private ListView mContentLv;
    private BGABanner mBanner;

    private Engine mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);
        mContentLv = (ListView) findViewById(R.id.lv_content);

        mEngine = App.getInstance().getEngine();

        initListView();


        loadBannerData();

    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        // 初始化HeaderView
        View headerView = View.inflate(this, R.layout.layout_header, null);
        mBanner = (BGABanner) headerView.findViewById(R.id.banner);
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(banner.getContext()).load(model).placeholder(R.drawable.holder).error(R.drawable.holder).dontAnimate().thumbnail(0.1f).into((ImageView) view);
            }
        });

        // 初始化ListView
        mContentLv.addHeaderView(headerView);
//        mContentLv.setAdapter(mContentAdapter);
    }

    /**
     * 加载头部广告条的数据
     */
    private void loadBannerData() {
        mEngine.fetchItemsWithItemCount(5).enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                mBanner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "加载广告条数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
