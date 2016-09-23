package com.wzdsqyy.applibDemo.ImageSelector;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wzdsqyy.applibDemo.R;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import static com.yuyh.library.imgsel.common.Constant.config;

public class ImageSelectorActivity extends AppCompatActivity {
    ImgSelConfig.Builder builder;
    private int REQUEST_CODE=1;
    private int REQUEST_CODE2=2;
    private static final String TAG = "ImageSelectorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);
        ImageLoader loader = new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
                Glide.with(context).load(path).into(imageView);
            }
        };

        // 自由配置选项
        builder = new ImgSelConfig.Builder(loader)
                // 是否多选
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#3F51B5"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机
                .needCamera(false)
                // 最大选择图片数量
                .maxNum(3);
    }

    public void single(View view){
        ImgSelConfig select = this.builder.multiSelect(false).build();
        ImgSelActivity.startActivity(this, select, REQUEST_CODE);
    }


    public void mutile(View view){
        ImgSelConfig select = this.builder.multiSelect(true).build();
        ImgSelActivity.startActivity(this, select, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
                StringBuilder tvResult=new StringBuilder();
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            for (String path : pathList) {
                tvResult.append(path + "\n");
            }
            Log.d(TAG, "onActivityResult: "+tvResult.toString());
        }
    }
}
