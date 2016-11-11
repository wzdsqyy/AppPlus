package com.wzdsqyy.weiman.ui.comics.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.agera.Receiver;
import com.google.android.agera.Supplier;
import com.wzdsqyy.bdj.R;
import com.wzdsqyy.mutiitem.MutiItemAdapter;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.ui.comics.itembinder.ComicsItemBinder;
import com.wzdsqyy.weiman.ui.comics.viewmodel.ComicsItemPresenter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsPageFragment extends Fragment implements Receiver<List<ComicsItem>>, Supplier<String>, MutiItemBinderFactory {
    private static final int SUCCESS = 1, LOADING = 2, EMPTY = 3, ERROR = 4;
    private int mStatus = LOADING;
    public static final String COMICS_TYPE_NAME = "Comics_Type_name";
    private String mName;
    private RecyclerView mRvList;
    private MutiItemAdapter<ComicsItem> rcAdapter;
    private View mLoading, mEmpty, mError;
    private ComicsItemPresenter presenter;

    public static ComicsPageFragment newInstance(String name) {
        ComicsPageFragment fragment = new ComicsPageFragment();
        fragment.mName = name;
        return fragment;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        rcAdapter = new MutiItemAdapter<>(this);
        rcAdapter.register(ComicsItem.class, R.layout.item_comics);
        if (savedInstanceState != null) {
            mName = (String) savedInstanceState.getCharSequence(COMICS_TYPE_NAME);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.release();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(COMICS_TYPE_NAME, mName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comics_page, container, false);
        mRvList = (RecyclerView) view.findViewById(R.id.comics_list);
        mLoading = view.findViewById(R.id.common_loading);
        mEmpty = view.findViewById(R.id.common_empty);
        mError = view.findViewById(R.id.common_failtrue);
        mError.findViewById(R.id.common_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    presenter.startLoading();
                    showLoading();
                }
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
        switch (mStatus) {
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

    private void showLoading() {
        mStatus = LOADING;
        if (getView() == null) {
            return;
        }
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
        mRvList.setVisibility(View.GONE);
        mLoading.bringToFront();
    }

    private void showError() {
        mStatus = ERROR;
        if (getView() == null) {
            return;
        }
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
    }

    private void showEmpety() {
        mStatus = EMPTY;
        if (getView() == null) {
            return;
        }
        mEmpty.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void showSucess() {
        mStatus = SUCCESS;
        if (getView() == null) {
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
            startLoadingData();
        }
    }

    private void startLoadingData() {
        if(presenter ==null){
            presenter = new ComicsItemPresenter(this, new Receiver<Throwable>() {
                @Override
                public void accept(@NonNull Throwable value) {
                    showError();
                }
            }, this);
            if (presenter.startLoading()) {
                showLoading();
            }
        }else {
            if(!presenter.isSuccessLoad()){
                presenter.startLoading();
            }
        }
    }

    @NonNull
    @Override
    public MutiItemBinder getMutiItemHolder(@LayoutRes int layoutRes) {
        return new ComicsItemBinder();
    }

    @Override
    public void accept(@NonNull List<ComicsItem> value) {
        if (value.size() == 0) {
            showEmpety();
        } else {
            showSucess();
            if (presenter.isMorePage()) {
                rcAdapter.addMoreData(value);
            } else {
                rcAdapter.setData(value);
            }
        }
    }

    @NonNull
    @Override
    public String get() {
        return mName;
    }
}
