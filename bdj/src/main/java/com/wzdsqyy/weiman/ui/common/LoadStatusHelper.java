package com.wzdsqyy.weiman.ui.common;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wzdsqyy.bdj.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/11/11.
 */

public class LoadStatusHelper {
    public static final int SUCCESS = 1, LOADING = 2, EMPTY = 3, ERROR = 4;
    public static int retry_btn=R.id.common_retry;
    public static int emptyRes=R.id.common_empty;
    public static int errorRes=R.id.common_failtrue;
    public static int loadRes=R.id.common_loading;
    public void release() {
        mStatus = LOADING;
        mLoading = null;
        mEmpty = null;
        mError = null;
        init = false;
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
    private boolean init = false;
    private OnRetryButtonListener mlistener;
    public LoadStatusHelper() {
    }
    public LoadStatusHelper(Activity activity) {
        init(activity);
        init = true;
    }

    public LoadStatusHelper(View view, OnRetryButtonListener listener) {
        init(view);
        this.mlistener = listener;
        init = true;
    }

    public LoadStatusHelper(Activity activity, OnRetryButtonListener listener) {
        init(activity);
        this.mlistener = listener;
        init = true;
    }

    public LoadStatusHelper setListener(OnRetryButtonListener listener) {
        this.mlistener = listener;
        return this;
    }

    public LoadStatusHelper setStatus(@Status int mStatus) {
        if (this.mStatus != mStatus) {
            this.mStatus = mStatus;
            if (init) {
                toStatus(mStatus);
            }
        }
        return this;
    }

    public void refresh() {
        toStatus(mStatus);
    }

    public void toStatus(@Status int status) {
        if (this.mStatus==status) {
            return;
        }
        mStatus = status;
        if(!init){
            return;
        }
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

    }

    public void init(View view) {
        mLoading = view.findViewById(loadRes);
        mEmpty = view.findViewById(emptyRes);
        mError = view.findViewById(errorRes);
        mError.findViewById(retry_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mlistener != null) {
                    mlistener.onRetryButtonClick(v);
                }
                mStatus = LOADING;
            }
        });
        init = true;
    }

    public void init(Activity activity) {
        mLoading = activity.findViewById(loadRes);
        mEmpty = activity.findViewById(emptyRes);
        mError = activity.findViewById(errorRes);
        mError.findViewById(retry_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatus = LOADING;
                if (mlistener != null) {
                    mlistener.onRetryButtonClick(v);
                }
            }
        });
        init = true;
    }

    private void showLoading() {
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
        mLoading.bringToFront();
    }

    private void showError() {
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
    }

    private void showEmpety() {
        mEmpty.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void showSucess() {
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    public static interface OnRetryButtonListener {
        void onRetryButtonClick(View v);
    }
}
