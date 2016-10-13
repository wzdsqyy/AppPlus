package com.wzdsqyy.applibDemo.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wzdsqyy.applibDemo.R;

/**
 * Created by Administrator on 2016/10/13.
 */

public class InnerListViewFragment extends Fragment{
    private ListView listView;
    private int index;
    public static InnerListViewFragment newInstance(int index) {
        InnerListViewFragment fragment = new InnerListViewFragment();
        fragment.index=index;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView view = (ListView) inflater.inflate(R.layout.fragment_listview, container, false);
        view.setAdapter(new ListAdapter(index));
//        NestedHelper.newInstance(getContext(),view).setInterceptTouchListener(new OrientationDisallowInterceptListener(getContext()));
        return view;
    }
}
