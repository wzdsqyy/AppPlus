package com.wzdsqyy.weiman.ui.common;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.view.View;

import com.wzdsqyy.bdj.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/11/11.
 */

public class LoadDataHelper {
    public static final int SUCCESS = 1, LOADING = 2, EMPTY = 3, ERROR = 4;

    public void release() {
         mStatus = LOADING;
         mLoading=null;
         mEmpty=null;
         mError=null;
         init=false;
    }

    @IntDef({SUCCESS, LOADING, EMPTY, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @Status
    private int mStatus = LOADING;
    private View mLoading;
    private View mEmpty;
    private View mError;
    private boolean init=false;
    public LoadDataHelper() {
    }

    public LoadDataHelper(View view, OnRetryButtonListener listener) {
        init(view, listener);
        init=true;
    }

    public LoadDataHelper(Activity activity, OnRetryButtonListener listener) {
        init(activity, listener);
        init=true;
    }

    public void refresh(@Status int status) {
        switch (status) {
            case EMPTY:
                showEmpety();
                break;
            case SUCCESS:
                showSucess();
                break;
            case ERROR:
                showError();
                break;
            default:
                showLoading();
                break;
        }
        mStatus=status;
    }

    public void init(View view, final OnRetryButtonListener listener) {
        mLoading = view.findViewById(R.id.common_loading);
        mEmpty = view.findViewById(R.id.common_empty);
        mError = view.findViewById(R.id.common_failtrue);
        mError.findViewById(R.id.common_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRetryButtonClick(v);
                }
                showLoading();
            }
        });
        init=true;
    }

    public void init(Activity activity, final OnRetryButtonListener listener) {
        mLoading = activity.findViewById(R.id.common_loading);
        mEmpty = activity.findViewById(R.id.common_empty);
        mError = activity.findViewById(R.id.common_failtrue);
        mError.findViewById(R.id.common_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRetryButtonClick(v);
                }
                showLoading();
            }
        });
        init=true;
    }

    public
    @Status
    int showLoading() {
        if(!init)return LOADING;
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
        mLoading.bringToFront();
        return LOADING;
    }

    public
    @Status
    int showError() {
        if(!init)return ERROR;
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        return ERROR;
    }

    public
    @Status
    int showEmpety() {
        if(!init)return EMPTY;
        mEmpty.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
        return EMPTY;
    }

    public
    @Status
    int showSucess() {
        if(!init)return SUCCESS;
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
        return SUCCESS;
    }

    public static interface OnRetryButtonListener {
        void onRetryButtonClick(View v);
    }
}
