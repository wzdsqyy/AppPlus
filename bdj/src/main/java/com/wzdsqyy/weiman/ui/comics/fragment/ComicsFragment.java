package com.wzdsqyy.weiman.ui.comics.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzdsqyy.bdj.R;
import com.wzdsqyy.weiman.ui.comics.adapter.ComicsPageAdapter;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsFragment extends Fragment {
    private TabLayout categotyTab;
    private ViewPager pager;
    private ComicsPageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ComicsPageAdapter(getFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comics, container, false);
        categotyTab= (TabLayout) view.findViewById(R.id.comics_category);
        pager= (ViewPager) view.findViewById(R.id.comics_page);
        pager.setAdapter(adapter);
        return view;
    }
}
