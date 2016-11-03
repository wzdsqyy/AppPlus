package com.wzdsqyy.applibDemo.MutiItemList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.sticky.SimpleAdapter;
import com.wzdsqyy.commonview.refresh.DefaultFooter;
import com.wzdsqyy.commonview.refresh.DefaultHeader;
import com.wzdsqyy.commonview.refresh.OnFreshListener;
import com.wzdsqyy.commonview.refresh.RefreshLayout;

public class PullRefershActivity extends AppCompatActivity implements OnFreshListener {
    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    private static final String TAG = "PullRefershActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh);
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.list_item);
        refreshLayout= (RefreshLayout) findViewById(R.id.activity_pull_refresh);
        recyclerView= (android.support.v7.widget.RecyclerView) findViewById(R.id.list_item);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        com.wzdsqyy.applibDemo.sticky.SimpleAdapter adapter=new SimpleAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        refreshLayout.setType(RefreshLayout.Type.FOLLOW);
        refreshLayout.setHeader(new DefaultHeader(this));
        refreshLayout.setFooter(new DefaultFooter(this));
        refreshLayout.setListener(this);
    }


    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.onFinishFreshAndLoad();
            }
        },1500);
    }

    @Override
    public void onLoadmore() {
        Log.d(TAG, "onLoadmore: ");
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.onFinishFreshAndLoad();
            }
        },1500);
    }
}
