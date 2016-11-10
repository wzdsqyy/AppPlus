package com.wzdsqyy.weiman.ui.comics.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.agera.ActivationHandler;
import com.google.android.agera.Observable;
import com.google.android.agera.Observables;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import com.google.android.agera.UpdateDispatcher;
import com.wzdsqyy.bdj.R;
import com.wzdsqyy.mutiitem.MutiItemAdapter;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.data.model.ComicsModel;
import com.wzdsqyy.weiman.data.model.ModelManager;
import com.wzdsqyy.weiman.ui.comics.itembinder.ComicsItemBinder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsPageFragment extends Fragment implements Observable, Updatable,ActivationHandler, MutiItemBinderFactory {

    private static final int SUCCESS=1,LOADING=2,EMPTY=3,ERROR=4;
    private String mName;
    private Repository<Result<List<ComicsItem>>> repository;
    private UpdateDispatcher dispatcher;
    private RecyclerView mRvList;
    private MutiItemAdapter<ComicsItem> rcAdapter;
    private View mLoading,mEmpty,mError;
    private int mStatus=LOADING;

    public static ComicsPageFragment newInstance(String name) {
        ComicsPageFragment fragment = new ComicsPageFragment();
        fragment.mName = name;
        fragment.dispatcher = Observables.updateDispatcher(fragment);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rcAdapter=new MutiItemAdapter<>(this);
        rcAdapter.register(ComicsItem.class,R.layout.item_comics);
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
                showLoading();
            }
        });
        rcAdapter.setViewLayoutManager(mRvList);
        mRvList.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showStatus(mStatus);
    }

    private void showStatus(int mStatus) {
        switch (mStatus){
            case EMPTY:
                showEmpety();
                break;
            case LOADING:
                showLoading();
                break;
            case ERROR:
                showError();
                break;
            default:
                showSucess();
                break;
        }
    }

    private void showLoading(){
        mStatus=LOADING;
        if(getView()==null){
            return;
        }
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
        mRvList.setVisibility(View.GONE);
        mLoading.bringToFront();
    }

    private void showError(){
        mStatus=ERROR;
        if(getView()==null){
            return;
        }
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
    }

    private void showEmpety(){
        mStatus=EMPTY;
        if(getView()==null){
            return;
        }
        mEmpty.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void showSucess(){
        mStatus=SUCCESS;
        if(getView()==null){
            return;
        }
        mRvList.setVisibility(View.VISIBLE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(repository==null){
                repository = ModelManager.getModel(ComicsModel.class,this).getByNameList("1",mName,this);
                repository.addUpdatable(this);
                showLoading();
            }else {
                if(repository.get().failed()){
                    dispatcher.update();
                    showLoading();
                }else {
                    showSucess();
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
        Result<List<ComicsItem>> result = repository.get();
        if (result.succeeded()) {
            if(result.get().size()==0){
                showEmpety();
            }else {
                showSucess();
                rcAdapter.setData(result.get());
            }
        } else {
            showError();
        }
    }

    @Override
    public void observableActivated(@NonNull UpdateDispatcher caller) {
        caller.addUpdatable(this);
    }

    @Override
    public void observableDeactivated(@NonNull UpdateDispatcher caller) {
        caller.removeUpdatable(this);
    }

    @NonNull
    @Override
    public MutiItemBinder getMutiItemHolder(@LayoutRes int layoutRes) {
        return new ComicsItemBinder();
    }
}
