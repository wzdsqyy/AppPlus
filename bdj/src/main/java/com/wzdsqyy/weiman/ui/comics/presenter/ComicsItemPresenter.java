package com.wzdsqyy.weiman.ui.comics.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.agera.Merger;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.wzdsqyy.utils.helper.ExecutorHelper;
import com.wzdsqyy.weiman.bean.ComicsItemPage;
import com.wzdsqyy.weiman.bean.ComicsItemResponse;
import com.wzdsqyy.weiman.data.Api;
import com.wzdsqyy.weiman.data.function.parser.ComicsItemFunction;
import com.wzdsqyy.weiman.data.model.ComicsModel;
import com.wzdsqyy.weiman.data.service.Comics;

/**
 * Created by Administrator on 2016/11/11.
 */

public class ComicsItemPresenter extends LoadPresenter<ComicsItemPage> implements Supplier<Integer>,Merger<Integer, String, Result<ComicsItemResponse>>{
    private int page=1;
    private boolean morepage=false;
    public ComicsItemPresenter(Supplier<String> name, CallBack<ComicsItemPage> callBack) {
        super(callBack);
        Repository<Result<ComicsItemPage>> compile = Repositories.repositoryWithInitialValue(Result.<ComicsItemPage>absent())
                .observe(this)
                .onUpdatesPerLoop()
                .goTo(ExecutorHelper.getHelper().getExecutor())
                .getFrom(this)
                .mergeIn(name, this)
                .thenTransform(new ComicsItemFunction())
                .compile();
        setCompile(compile);
    }

    public boolean startLoading(){
        return startLoading(1);
    }

    public boolean isMorePage(){
        return morepage;
    }

    public boolean startLoading(int page){
        if(isMorePage()){
            return false;
        }
        this.page=page;

        return super.startLoading();
    }

    @NonNull
    @Override
    public Integer get() {
        return page;
    }

    @NonNull
    @Override
    public Result<ComicsItemResponse> merge(@NonNull Integer integer, @NonNull String s) {
        Result<ComicsItemResponse> result = Api.get().getApi(Comics.class).getCategoryList(integer.toString(), ComicsModel.getType(s)).get();
        if(integer>1){
            morepage=true;
        }else {
            morepage=false;
        }
        return result;
    }
}
