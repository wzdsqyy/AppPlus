package com.wzdsqyy.commonview.drawable;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Qiuyy on 2016/9/13.
 */
public class TabDrawable extends BaseDrawable implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    private Rect mIndicatorRect = new Rect();
    private Drawable mIndicator;
    private ViewGroup mTabsContainer;
    private int mCurrentTab = 0;
    private int newCurrentTab = mCurrentTab;
    private int mIndicatorWidth = -1;
    private ValueAnimator animator;
    private OnTabSelectListener tabSelectListener;
    private float mRadio = 0;
    private int mEnd = -1;
    private int mStart = -1;

    private TabDrawable(Drawable drawable, @NonNull ViewGroup tabsContainer) {
        super(drawable);
        mTabsContainer = tabsContainer;
        tabsContainer.setBackgroundDrawable(this);
        setIndicator(new ColorDrawable(0xffff0000));
    }

    public static TabDrawable setTabsContainer(@NonNull ViewGroup mTabsContainer) {
        Drawable drawable = mTabsContainer.getBackground();
        TabDrawable tabDrawable = new TabDrawable(drawable, mTabsContainer);
        return tabDrawable;
    }

    public TabDrawable setIndicator(Drawable mIndicator) {
        if(this.mIndicator==mIndicator){
            return this;
        }
        this.mIndicator = mIndicator;
        calcIndicatorRect();
        mIndicator.setBounds(mIndicatorRect);
        invalidateSelf();
        return this;
    }

    public TabDrawable setTabSelectListener(OnTabSelectListener tabSelectListener) {
        this.tabSelectListener = tabSelectListener;
        return this;
    }

    private void calcIndicatorRect() {
        if (mCurrentTab == -1 || mTabsContainer == null || mCurrentTab > mTabsContainer.getChildCount()) {
            return;
        }
        View view = mTabsContainer.getChildAt(this.mCurrentTab);
        float left = view.getLeft();
        float right = view.getRight();
        if(mIndicatorWidth <= 0){
            mIndicatorWidth = view.getWidth();
        }else {
            left =left + (view.getWidth() - mIndicatorWidth) / 2f;
            right=left+mIndicatorWidth;
        }
        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(mIndicatorRect.isEmpty()){
            calcIndicatorRect();
        }
        mIndicator.setBounds(mIndicatorRect);
        canvas.drawCircle(mIndicatorRect.centerX(),mIndicatorRect.centerY(),20,new Paint());
//        mIndicator.draw(canvas);
    }

    @Override
    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
    }

    public void setCurrentTab(int currentTab, boolean anim) {
        if (mCurrentTab == currentTab) {
            return;
        } else if (currentTab < 0) {
            currentTab = 0;
        } else if (mCurrentTab > mTabsContainer.getChildCount() - 1) {
            currentTab = mTabsContainer.getChildCount() - 1;
        }
        this.newCurrentTab = currentTab;
        if (animator != null && animator.isRunning()) {
            animator.end();
        }
        if (anim) {
            if (mEnd == -1) {
                mEnd = getTabCenterX(currentTab);
            }
            if (mStart == -1) {
                mStart = getTabCenterX(mCurrentTab);
            }
            animator = ValueAnimator.ofInt(0, mEnd - mStart);
            animator.addUpdateListener(this);
            animator.addListener(this);
            animator.start();
        } else {
            mCurrentTab = newCurrentTab;
            invalidateSelf();
            if (tabSelectListener != null) {
                tabSelectListener.onTabSelect(mCurrentTab);
            }
        }
    }

    private int getTabCenterX(int index) {
        View des = mTabsContainer.getChildAt(index);
        return des.getLeft() + des.getWidth() / 2;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int dx = (int) animation.getAnimatedValue();
        if (tabSelectListener != null) {
            tabSelectListener.onTabOffset(mCurrentTab, animation.getAnimatedFraction());
        }
        calcIndicatorRect();
        mIndicatorRect.offset(dx,0);
        invalidateSelf();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mCurrentTab = newCurrentTab;
        if (tabSelectListener != null) {
            tabSelectListener.onTabSelect(mCurrentTab);
        }
    }

    public void moving(@FloatRange(from = -1f, to = 1f) float radio) {
        mRadio = radio;
        if (mStart == -1 || mEnd == -1) {
            int endIndex = mCurrentTab;
            if (radio < 0) {
                endIndex--;
            } else {
                endIndex++;
            }
            if (endIndex < 0) {
                endIndex = 0;
            } else if (endIndex > mTabsContainer.getChildCount() - 1) {
                endIndex = mTabsContainer.getChildCount() - 1;
            }
            newCurrentTab = endIndex;
            mEnd = getTabCenterX(endIndex);
            mStart = getTabCenterX(mCurrentTab);
        } else {
            calcIndicatorRect();
            int dx = (int) ((mEnd - mStart) * radio);
            mIndicatorRect.offset(dx,0);
            invalidateSelf();
        }
    }

    public void stopMoveing() {
        mEnd = -1;
        mStart = -1;
        if (mRadio < 0.5f || mRadio > -0.5f) {
            newCurrentTab = mCurrentTab;
        }
        animator = ValueAnimator.ofInt(mEnd - mStart, 0);
        animator.addUpdateListener(this);
        animator.addListener(this);
        animator.start();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}

