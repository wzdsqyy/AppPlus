package com.wzdsqyy.applibDemo.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzdsqyy.applib.ui.nested.NestedHelper;
import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.viewpager.listener.InnerViewPager;

/**
 * Created by Administrator on 2016/10/13.
 */

public class InnerViewPagerFragment extends Fragment{
    private ViewPager innerviewPager;
    private int index;
    public static InnerViewPagerFragment newInstance(int index) {
        InnerViewPagerFragment fragment = new InnerViewPagerFragment();
        fragment.index=index;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        innerviewPager= (ViewPager) view.findViewById(R.id.innerviewPager);
        innerviewPager.setAdapter(new MyAdapter(index+3));
        TextView textView= (TextView) view.findViewById(R.id.notice);
        textView.setText(getClass().getSimpleName()+index);
        NestedHelper.newInstance(getContext(),innerviewPager).setInterceptTouchListener(new InnerViewPager(innerviewPager));
        return view;
    }
}
