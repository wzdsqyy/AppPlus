package com.wzdsqyy.weiman.data.model;

import android.support.annotation.NonNull;

import com.google.android.agera.Function;
import com.google.android.agera.Observable;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.wzdsqyy.utils.helper.ExecutorHelper;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.bean.ComicsItemPage;
import com.wzdsqyy.weiman.bean.ComicsItemResponse;
import com.wzdsqyy.weiman.data.Api;
import com.wzdsqyy.weiman.data.function.parser.ComicsFunction;
import com.wzdsqyy.weiman.data.function.parser.ComicsItemFunction;
import com.wzdsqyy.weiman.data.service.Comics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsModel extends BaseModel {
    public static ArrayList<String> types = new ArrayList<>(13);
    public static ArrayList<String> names = new ArrayList<>(13);

    static {
        types.add("/category/weimanhua/kbmh");
        names.add("恐怖漫画");
        types.add("/category/weimanhua/gushimanhua");
        names.add("故事漫画");
        types.add("/category/duanzishou");
        names.add("段子手");
        types.add("/category/lengzhishi");
        names.add("冷知识");
        types.add("/category/qiqu");
        names.add("奇趣");
        types.add("/category/dianying");
        names.add("电影");
        types.add("/category/gaoxiao");
        names.add("搞笑");
        types.add("/category/mengchong");
        names.add("萌宠");
        types.add("/category/xinqi");
        names.add("新奇");
        types.add("/category/huanqiu");
        names.add("环球");
        types.add("/category/sheying");
        names.add("摄影");
        types.add("/category/wanyi");
        names.add("玩艺");
        types.add("/category/chahua");
        names.add("插画");
    }

    public ComicsModel newInstance() {
        ComicsModel fragment = new ComicsModel();
        return fragment;
    }

    public static ArrayList<String> getNames() {
        return names;
    }

    public static String getType(String name){
        int index = names.indexOf(name);
        return types.get(index);
    }

    public  Repository<Result<ComicsItemPage>> getByNameList(String page, String name, Observable observable) {
        return getListByType(page,getType(name),observable);
    }

    public  Repository<Result<ComicsItemPage>> getListByType(String page,Observable observable){
        return getListByType(page,types.get(1),observable);
    }

    public  Repository<Result<ComicsItemPage>> getListByType(String page, String type, Observable observable) {
        return Repositories.repositoryWithInitialValue(Result.<ComicsItemPage>absent())
                .observe(observable)
                .onUpdatesPerLoop()
                .goTo(ExecutorHelper.getHelper().getExecutor())
                .getFrom(Api.get().getApi(Comics.class).getCategoryList(page, type))
                .thenTransform(new ComicsItemFunction())
                .compile();
    }

    public static Repository<Result<com.wzdsqyy.weiman.bean.Comics>> getComics(String id,Observable observable) {
        return Repositories.repositoryWithInitialValue(Result.<com.wzdsqyy.weiman.bean.Comics>absent())
                .observe(observable)
                .onUpdatesPerLoop()
                .goTo(ExecutorHelper.getHelper().getExecutor())
                .getFrom(Api.get().getApi(Comics.class).getDetail(id))
                .thenTransform(new ComicsFunction())
                .compile();
    }
}
