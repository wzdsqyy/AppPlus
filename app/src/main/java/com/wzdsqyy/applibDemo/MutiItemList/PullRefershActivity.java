package com.wzdsqyy.applibDemo.MutiItemList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.sticky.SimpleAdapter;
import com.wzdsqyy.commonview.refresh.RefreshListener;

public class PullRefershActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    private static final String TAG = "PullRefershActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh);
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.list_item);
        RefreshListener listener=new RefreshListener((ViewGroup) recyclerView.getParent());
        recyclerView.setOnTouchListener(listener);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        com.wzdsqyy.applibDemo.sticky.SimpleAdapter adapter=new SimpleAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
