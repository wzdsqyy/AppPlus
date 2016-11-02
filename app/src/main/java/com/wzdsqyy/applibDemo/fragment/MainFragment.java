package com.wzdsqyy.applibDemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.fragment.BaseFragment;
import com.wzdsqyy.fragment.ContentPage;
import com.wzdsqyy.fragment.PageManager;
import com.wzdsqyy.fragment.TabbarManager;

/**
 * Created by Administrator on 2016/11/2.
 */

public class MainFragment extends BaseFragment implements ContentPage,View.OnClickListener {
    private TabbarManager manager;
    private int index=0;
    private static final String TAG = "MainFragment";

    public MainFragment setIndex(int index) {
        this.index = index;
        return this;
    }

    public static MainFragment newInstance(int index) {
        MainFragment fragment = new MainFragment();
        fragment.index=index;
        return fragment;
    }

    @Override
    public int getContentId() {
        return R.id.main_page_containr;
    }

    public @Nullable TabbarManager getManager() {
        if(!isAdded()){
            return null;
        }
        return manager;
    }

    @Override
    public FragmentManager getPageFragmentManager() {
        return getChildFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        TextView index = (TextView) view.findViewById(R.id.index_notice);
        view.findViewById(R.id.button_one).setOnClickListener(this);
        view.findViewById(R.id.button_three).setOnClickListener(this);
        view.findViewById(R.id.button_two).setOnClickListener(this);
        view.findViewById(R.id.button_four).setOnClickListener(this);
        index.setText("index"+this.index);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_one:
                manager.showPage(0);
                break;
            case R.id.button_two:
                manager.showPage(1);
                break;
            case R.id.button_three:
                manager.showPage(2);
                break;
            case R.id.button_four:
                manager.showPage(3);
                break;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager= PageManager.attachTabbar(this);
        manager.addPage(TabFragment.newInstance(0))
                .addPage(TabFragment.newInstance(1))
                .addPage(TabFragment.newInstance(2))
                .addPage(TabFragment.newInstance(3))
                .commit();
        Log.d(TAG, "onCreate: "+index);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: "+index);
    }
}
