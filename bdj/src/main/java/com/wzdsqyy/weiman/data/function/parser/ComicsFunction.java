package com.wzdsqyy.weiman.data.function.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.Result;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.bean.ComicsItemResponse;
import com.wzdsqyy.weiman.bean.Comics;
import com.wzdsqyy.weiman.bean.ComicsResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class ComicsFunction implements Function<Result<ComicsResponse>, Result<Comics>> {
    private static final String TAG = "ComicsFunction";
    @NonNull
    @Override
    public Result<Comics> apply(@NonNull Result<ComicsResponse> input) {
        if(input.succeeded()&&input.get().body.code==0){
            Comics list=input.get().body.item;
            return Result.success(list);
        }
        return Result.failure();
    }
}
