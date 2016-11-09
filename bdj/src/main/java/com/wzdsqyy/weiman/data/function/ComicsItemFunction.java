package com.wzdsqyy.weiman.data.function;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.Result;
import com.wzdsqyy.weiman.bean.BaseResponse;
import com.wzdsqyy.weiman.bean.ComicsItem;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsItemFunction implements Function<Result<BaseResponse>, Result<List<ComicsItem>>> {
    private static final String TAG = "ComicsItemFunction";
    @NonNull
    @Override
    public Result<List<ComicsItem>> apply(@NonNull Result<BaseResponse> input) {
        Log.d(TAG, "apply: "+input.get());
        return Result.failure();
    }
}
