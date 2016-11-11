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

import com.google.android.agera.Supplier;
import com.wzdsqyy.bdj.R;
import com.wzdsqyy.mutiitem.MutiItemAdapter;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.bean.ComicsItemPage;
import com.wzdsqyy.weiman.ui.comics.itembinder.ComicsItemBinder;
import com.wzdsqyy.weiman.ui.comics.presenter.CallBack;
import com.wzdsqyy.weiman.ui.comics.presenter.ComicsItemPresenter;
import com.wzdsqyy.weiman.ui.common.LoadDataHelper;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsPageFragment extends Fragment implements Supplier<String>, MutiItemBinderFactory, CallBack<ComicsItemPage>,LoadDataHelper.OnRetryButtonListener{
    public static final String COMICS_TYPE_NAME = "Comics_Type_name";
    private String mName;
    private RecyclerView mRvList;
    private MutiItemAdapter<ComicsItem> rcAdapter;
    private ComicsItemPresenter presenter;
    private LoadDataHelper helper;
    @LoadDataHelper.Status
    private int mStatus=LoadDataHelper.LOADING;

    public static ComicsPageFragment newInstance(String name) {
        ComicsPageFragment fragment = new ComicsPageFragment();
        fragment.mName = name;
        fragment.helper=new LoadDataHelper();
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
        rcAdapter.setViewLayoutManager(mRvList);
        mRvList.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        helper.release();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper.init(view,this);
        helper.refresh(mStatus);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            startLoadingData();
        }
    }

    private void startLoadingData() {
        if (presenter == null) {
            presenter = new ComicsItemPresenter(this, this);
            if (presenter.startLoading()) {
                mStatus=helper.showLoading();
            }
        } else {
            if (!presenter.isSuccessLoad()) {
                presenter.startLoading();
            }
        }
    }

    @NonNull
    @Override
    public MutiItemBinder getMutiItemHolder(@LayoutRes int layoutRes) {
        return new ComicsItemBinder();
    }

    @NonNull
    @Override
    public String get() {
        return mName;
    }

    @Override
    public void onLoadError(Throwable ex) {
        mStatus=helper.showError();
    }

    @Override
    public void onSuccess(ComicsItemPage value) {
        if (value.items.size() == 0) {
            mStatus=helper.showEmpety();
        } else {
            mStatus=helper.showSucess();
            if (presenter.isMorePage()) {
                rcAdapter.addMoreData(value.items);
            } else {
                rcAdapter.setData(value.items);
            }
        }
    }

    @Override
    public void onRetryButtonClick(View v) {
        if (presenter != null) {
            presenter.startLoading();
            mStatus=helper.showLoading();
        }
    }
}
