package com.wzdsqyy.weiman.data.function.parser;

import android.support.annotation.NonNull;

import com.google.android.agera.Function;
import com.google.android.agera.Result;
import com.wzdsqyy.weiman.bean.ComicsItemResponse;
import com.wzdsqyy.weiman.bean.Comics;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ComicsFunction implements Function<Result<ComicsItemResponse>, Result<Comics>> {
    private static final String TAG = "ComicsFunction";
    @NonNull
    @Override
    public Result<Comics> apply(@NonNull Result<ComicsItemResponse> input) {
        return Result.failure();
    }
}
