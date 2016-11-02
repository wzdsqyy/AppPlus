package com.wzdsqyy.applibDemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzdsqyy.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/11/2.
 */

public class TabFragment extends BaseFragment {
    String index;
    private static final String TAG = "TabFragment";

    public static TabFragment newInstance(int index) {
        TabFragment fragment = new TabFragment();
        fragment.index = index+"";
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("TabFragment" + index);
        return textView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: "+index);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: "+index);
    }
}
