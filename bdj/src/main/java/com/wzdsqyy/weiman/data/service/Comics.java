package com.wzdsqyy.weiman.data.service;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.wzdsqyy.weiman.bean.BaseResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 黑白漫画 接口
 */

public interface Comics {
    @GET("/958-1")
    Supplier<Result<BaseResponse>> getCategoryList(@Query("page") String page, @Query("type")String type);
    @GET("/958-2")
    Supplier<Result<BaseResponse>> getDetail(@Query("id") String id);
}
