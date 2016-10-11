package com.wzdsqyy.applib.ui.transformer;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/19 14:35
 * 描述:
 */
public abstract class BGAPageTransformer implements ViewPager.PageTransformer {
    public static boolean hasNineOld = true;

    static {
        Class<?> clazz;
        try {
            clazz = Class.forName("com.nineoldandroids.view.ViewHelper");
            if(clazz==null){
                hasNineOld=false;
            }
        } catch (ClassNotFoundException e) {
            hasNineOld = false;
        }
        if(!hasNineOld&&Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            throw new RuntimeException("你应该导入 nineoldandroids 动画库 加入\" " +
                    "dependencies {\n" +
                    "    compile 'com.nineoldandroids:library:2.4.0'\n" +
                    "}   \" 或调高最低android 版本 11 以上");
        }
    }

    public static void setPivotX(View view, float width) {
        if(hasNineOld){
            ViewHelper.setPivotX(view,width);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setPivotX(width);
            }
        }
    }

    public static void setScaleX(View view, float v) {
        if(hasNineOld){
            ViewHelper.setScaleX(view,v);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setScaleX(v);
            }
        }
    }

    public static void setAlpha(View view, float i) {
        if(hasNineOld){
            ViewHelper.setAlpha(view,i);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setAlpha(i);
            }
        }
    }

    public static void setRotationY(View view, float rotationY) {
        if(hasNineOld){
            ViewHelper.setRotationY(view,rotationY);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setRotationY(rotationY);
            }
        }
    }

    public static void setPivotY(View view, float pivotY) {
        if(hasNineOld){
            ViewHelper.setPivotY(view,pivotY);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setPivotY(pivotY);
            }
        }
    }

    public static void setRotation(View view, float rotation) {
        if(hasNineOld){
            ViewHelper.setRotation(view,rotation);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setRotation(rotation);
            }
        }
    }

    public static void setScaleY(View view, float scaleY) {
        if(hasNineOld){
            ViewHelper.setScaleY(view,scaleY);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setScaleY(scaleY);
            }
        }
    }

    public static void setTranslationX(View view, float translationX) {
        if(hasNineOld){
            ViewHelper.setTranslationX(view,translationX);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setTranslationX(translationX);
            }
        }
    }

    public static void setTranslationY(View view, float translationY) {
        if(hasNineOld){
            ViewHelper.setTranslationY(view,translationY);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setTranslationY(translationY);
            }
        }
    }

    public static void setRotationX(View view, float rotationX) {
        if(hasNineOld){
            ViewHelper.setRotationX(view,rotationX);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setRotationX(rotationX);
            }
        }
    }

    public void transformPage(View view, float position) {
        if (position < -1.0f) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            handleInvisiblePage(view, position);
        } else if (position <= 0.0f) {
            // [-1,0]
            // Use the default slide transition when moving to the left page
            handleLeftPage(view, position);
        } else if (position <= 1.0f) {
            // (0,1]
            handleRightPage(view, position);
        } else {
            // (1,+Infinity]
            // This page is way off-screen to the right.
            handleInvisiblePage(view, position);
        }
    }

    public abstract void handleInvisiblePage(View view, float position);

    public abstract void handleLeftPage(View view, float position);

    public abstract void handleRightPage(View view, float position);

    public static BGAPageTransformer getPageTransformer(TransitionEffect effect) {
        switch (effect) {
            case Default:
                return new DefaultPageTransformer();
            case Alpha:
                return new AlphaPageTransformer();
            case Rotate:
                return new RotatePageTransformer();
            case Cube:
                return new CubePageTransformer();
            case Flip:
                return new FlipPageTransformer();
            case Accordion:
                return new AccordionPageTransformer();
            case ZoomFade:
                return new ZoomFadePageTransformer();
            case Fade:
                return new FadePageTransformer();
            case ZoomCenter:
                return new ZoomCenterPageTransformer();
            case ZoomStack:
                return new ZoomStackPageTransformer();
            case Stack:
                return new StackPageTransformer();
            case Depth:
                return new DepthPageTransformer();
            case Zoom:
                return new ZoomPageTransformer();
            default:
                return new DefaultPageTransformer();
        }
    }
}