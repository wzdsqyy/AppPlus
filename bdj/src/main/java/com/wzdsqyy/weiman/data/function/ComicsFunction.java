package com.wzdsqyy.weiman.data.function;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.Result;
import com.google.gson.JsonObject;
import com.wzdsqyy.weiman.bean.BaseResponse;
import com.wzdsqyy.weiman.bean.Comics;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ComicsFunction implements Function<Result<BaseResponse>, Result<Comics>> {
    private static final String TAG = "ComicsFunction";
    @NonNull
    @Override
    public Result<Comics> apply(@NonNull Result<BaseResponse> input) {
        return Result.failure();
    }
}
