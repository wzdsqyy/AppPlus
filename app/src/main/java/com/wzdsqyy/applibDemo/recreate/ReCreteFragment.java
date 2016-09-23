package com.wzdsqyy.applibDemo.recreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzdsqyy.applibDemo.R;

/**
 * Created by Administrator on 2016/9/22.
 */

public class ReCreteFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ReCreteFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recreate, container, false);
        view.findViewById(R.id.custom).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: "+getActivity());
        Log.d(TAG, "onClick: "+getContext());
    }
}
