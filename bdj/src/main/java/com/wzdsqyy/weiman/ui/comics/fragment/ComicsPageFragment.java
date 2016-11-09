package com.wzdsqyy.weiman.ui.comics.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.agera.Observable;
import com.google.android.agera.Observables;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import com.google.android.agera.UpdateDispatcher;
import com.wzdsqyy.bdj.R;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.data.model.ComicsModel;
import com.wzdsqyy.weiman.ui.comics.adapter.ComicsPageAdapter;

import java.util.List;

import static android.R.id.empty;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsPageFragment extends Fragment implements Observable, Updatable {

    private String mName;
    private Repository<Result<List<ComicsItem>>> repository;
    private UpdateDispatcher dispatcher;
    private RecyclerView mRvList;
    private View mLoading,mEmpty,mError;
    private Result<List<ComicsItem>> result;

    public static ComicsPageFragment newInstance(String name) {
        ComicsPageFragment fragment = new ComicsPageFragment();
        fragment.mName = name;
        fragment.dispatcher = Observables.updateDispatcher();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comics_page, container, false);
        mRvList = (RecyclerView) view.findViewById(R.id.comics_list);
        mLoading=view.findViewById(R.id.common_loading);
        mEmpty=view.findViewById(R.id.common_empty);
        mError=view.findViewById(R.id.common_failtrue);
        mError.findViewById(R.id.common_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatcher.update();
            }
        });
        return view;
    }

    private void showLoading(){
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
    }

    private void showError(){
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
    }

    private void showEmpety(){
        mEmpty.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void showSucess(){
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && repository == null) {
            if(repository==null){
                repository = ComicsModel.getList("", ComicsModel.getType(mName), this);
                repository.addUpdatable(this);
                showLoading();
            }else {
                if(result.failed()){
                    dispatcher.update();
                }
            }
        }
    }

    @Override
    public void addUpdatable(@NonNull Updatable updatable) {
        dispatcher.addUpdatable(updatable);
    }

    @Override
    public void removeUpdatable(@NonNull Updatable updatable) {
        dispatcher.removeUpdatable(updatable);
    }

    @Override
    public void update() {
        result = repository.get();
        if (result.succeeded()) {
            if(result.get().size()==0){
                showEmpety();
            }else {
                showSucess();
                // TODO: 2016/11/9 显示数据
            }
        } else {
            showError();
        }
    }
}
