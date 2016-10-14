package com.wzdsqyy.applib.ui.banner;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.wzdsqyy.applib.ui.banner.transformer.BGAPageTransformer;

import java.util.List;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/7/5 上午11:34
 * 描述:
 */
public class BGABannerUtil {

    private BGABannerUtil() {
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public static ImageView getItemImageView(Context context, @DrawableRes int placeholderResId) {
        return getItemImageView(context, placeholderResId, ImageView.ScaleType.CENTER_CROP);
    }

    public static ImageView getItemImageView(Context context, @DrawableRes int placeholderResId, ImageView.ScaleType scaleType) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(placeholderResId);
        imageView.setClickable(true);
        imageView.setScaleType(scaleType);
        return imageView;
    }

    public static void resetPageTransformer(List<? extends View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
            BGAPageTransformer.setAlpha(view, 1);
            BGAPageTransformer.setPivotX(view, view.getMeasuredWidth() * 0.5f);
            BGAPageTransformer.setPivotY(view, view.getMeasuredHeight() * 0.5f);
            BGAPageTransformer.setTranslationX(view, 0);
            BGAPageTransformer.setTranslationY(view, 0);
            BGAPageTransformer.setScaleX(view, 1);
            BGAPageTransformer.setScaleY(view, 1);
            BGAPageTransformer.setRotationX(view, 0);
            BGAPageTransformer.setRotationY(view, 0);
            BGAPageTransformer.setRotation(view, 0);
        }
    }

}