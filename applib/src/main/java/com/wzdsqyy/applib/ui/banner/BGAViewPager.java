package com.wzdsqyy.applib.ui.banner;

import android.content.Context;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/19 11:23
 * 描述:继承ViewPager，通过反射方式实现支持低版本上切换动画
 */
public class BGAViewPager extends ViewPager {
    private boolean mAllowUserScrollable = true;
    private AutoPlayDelegate mAutoPlayDelegate;
    private BGABannerScroller scroller;
    private static Field mScrollField;
    private static Field pageTransformerField;
    private static Field mVelocityTrackerField;
    private static Field activePointerIdField;
    private static Field maximumVelocityField;
    private static Field drawingOrderField;
    private static Method setCurrentItemInternalMethod;
    private static Method populateMethod;
    private static Method setChildrenDrawingOrderEnabledCompatMethod;
    static {
        try {
            mScrollField = ViewPager.class.getDeclaredField("mScroller");
            mScrollField.setAccessible(true);
            pageTransformerField = ViewPager.class.getDeclaredField("mPageTransformer");
            pageTransformerField.setAccessible(true);
            setCurrentItemInternalMethod = ViewPager.class.getDeclaredMethod("setCurrentItemInternal", int.class, boolean.class, boolean.class);
            setCurrentItemInternalMethod.setAccessible(true);
            mVelocityTrackerField = ViewPager.class.getDeclaredField("mVelocityTracker");
            mVelocityTrackerField.setAccessible(true);
            activePointerIdField = ViewPager.class.getDeclaredField("mActivePointerId");
            activePointerIdField.setAccessible(true);
            maximumVelocityField = ViewPager.class.getDeclaredField("mMaximumVelocity");
            maximumVelocityField.setAccessible(true);
            populateMethod = ViewPager.class.getDeclaredMethod("populate");
            setChildrenDrawingOrderEnabledCompatMethod = ViewPager.class.getDeclaredMethod("setChildrenDrawingOrderEnabledCompat", boolean.class);
            setChildrenDrawingOrderEnabledCompatMethod.setAccessible(true);
            drawingOrderField = ViewPager.class.getDeclaredField("mDrawingOrder");
            drawingOrderField.setAccessible(true);
        }catch (Exception e) {

        }
    }

    public BGAViewPager(Context context) {
        super(context);
    }

    public BGAViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BGABannerScroller getScroller() {
        if(scroller==null){
            scroller= new BGABannerScroller(getContext());
            set(this,mScrollField,scroller);
        }
        return scroller;
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
            boolean hasTransformer = transformer != null;
        try {
            PageTransformer mPageTransformer = (PageTransformer) pageTransformerField.get(this);
            boolean needsPopulate = hasTransformer != (mPageTransformer != null);
            pageTransformerField.set(this, transformer);
            setChildrenDrawingOrderEnabledCompatMethod.invoke(this, hasTransformer);
            if (hasTransformer) {
                drawingOrderField.setInt(this, reverseDrawingOrder ? 2 : 1);
            } else {
                drawingOrderField.setInt(this, 0);
            }
            if (needsPopulate) {
                populateMethod.invoke(this);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 设置调用setCurrentItem(int item, boolean smoothScroll)方法时，page切换的时间长度
     *
     * @param duration page切换的时间长度
     */
    public void setPageChangeDuration(int duration) {
        getScroller().setDuration(duration);
    }

    /**
     * 切换到指定索引的页面，主要用于自动轮播
     *
     * @param position
     */
    public void setBannerCurrentItemInternal(int position) {
        invoke(this,setCurrentItemInternalMethod,position,true,true);
    }

    /**
     * 设置是否允许用户手指滑动
     *
     * @param allowUserScrollable true表示允许跟随用户触摸滑动，false反之
     */
    public void setAllowUserScrollable(boolean allowUserScrollable) {
        mAllowUserScrollable = allowUserScrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mAllowUserScrollable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mAllowUserScrollable) {
            if (mAutoPlayDelegate != null && (ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_UP)) {
                mAutoPlayDelegate.handleAutoPlayActionUpOrCancel(getXVelocity());
                return false;
            } else {
                return super.onTouchEvent(ev);
            }
        } else {
            return false;
        }
    }

    private float getXVelocity() {
        float xVelocity = 0;
        try {
            VelocityTracker velocityTracker = (VelocityTracker) mVelocityTrackerField.get(this);
            int maximumVelocity = maximumVelocityField.getInt(this);
            velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
            xVelocity = VelocityTrackerCompat.getXVelocity(velocityTracker, activePointerIdField.getInt(this));
        } catch (Exception e) {

        }
        return xVelocity;
    }

    public void setAutoPlayDelegate(AutoPlayDelegate autoPlayDelegate) {
        mAutoPlayDelegate = autoPlayDelegate;
    }

    public interface AutoPlayDelegate {
        void handleAutoPlayActionUpOrCancel(float xVelocity);
    }

    public void invoke(Object obj,Method method,Object... objs){
        try {
            if(!method.isAccessible()){
                method.setAccessible(true);
            }
            method.invoke(obj, method,objs);
        } catch (Exception e) {
        }
    }

    public void set(Object obj,Field filed,Object value){
        try {
            if(!filed.isAccessible()){
                filed.setAccessible(true);
            }
            filed.set(obj,value);
        } catch (Exception e) {
        }
    }

    public <T> T get(Object obj,Field filed,Class<T> clazz){
        try {
            if(!filed.isAccessible()){
                filed.setAccessible(true);
            }
            return (T) filed.get(obj);
        } catch (Exception e) {
        }
        return null;
    }
}