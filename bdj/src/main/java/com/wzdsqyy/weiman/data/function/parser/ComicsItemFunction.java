package com.wzdsqyy.weiman.data.function.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.Result;
import com.wzdsqyy.weiman.bean.ComicsItemPage;
import com.wzdsqyy.weiman.bean.ComicsItemResponse;
import com.wzdsqyy.weiman.bean.ComicsItem;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsItemFunction implements Function<Result<ComicsItemResponse>, Result<ComicsItemPage>> {
    private static final String TAG = "ComicsItemFunction";
    @NonNull
    @Override
    public Result<ComicsItemPage> apply(@NonNull Result<ComicsItemResponse> input) {
        if(input.succeeded()&&input.get().body.code==0){
            ComicsItemPage list=input.get().body.page;
            return Result.success(list);
        }
        return Result.failure();
    }
}
