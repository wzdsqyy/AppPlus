package com.wzdsqyy.weiman.data.model;

import android.support.annotation.NonNull;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/9.
 */

public class TypeName implements MutableRepository<Result<String>> {
    static ArrayList<String> types = new ArrayList<>(13);
    static ArrayList<String> names = new ArrayList<>(13);
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
    private Result<String> name,value;
    @NonNull
    @Override
    public Result<String> get() {
        return null;
    }

    @Override
    public void addUpdatable(@NonNull Updatable updatable) {

    }

    @Override
    public void removeUpdatable(@NonNull Updatable updatable) {

    }

    @Override
    public void accept(@NonNull Result<String> value) {

    }
}
