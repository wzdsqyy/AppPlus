package com.wzdsqyy.weiman.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.drakeet.retrofit2.adapter.agera.AgeraCallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/11/9.
 */

public class Api {
    public static Api api = new Api();
    private OkHttpClient client;
    private GsonBuilder builder = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls();

    public Api() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new SignInterceptor("19058", "55f4595a7c494de2b6e198d386b65ff8"))
                .build();
    }

    public static Api get() {
        return api;
    }

    public Gson getGson() {
        return builder.create();
    }

    public <T> T getApi(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(AgeraCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .baseUrl("http://route.showapi.com")
                .build();
        return retrofit.create(clazz);
    }
}
