package com.wzdsqyy.commonview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/10/12.
 */

public class VerticalViewPager extends ViewPager implements ViewPager.PageTransformer {
    private boolean isVertical = false;

    public VerticalViewPager(Context context) {
        super(context);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalViewPager setVertical(boolean vertical) {
        isVertical = vertical;
        if (isVertical) {
            setPageTransformer(true, this);
            setOverScrollMode(OVER_SCROLL_NEVER);
        }else {
            setPageTransformer(false,null);
        }
        return this;
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();
        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;
        event.setLocation(swappedX, swappedY);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isVertical) {
            swapTouchEvent(event);
            boolean intercept = super.onInterceptTouchEvent(event);
            if (intercept) {
                return true;
            }
            swapTouchEvent(event);
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isVertical) {
            event = swapTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void transformPage(View view, float position) {
        float alpha = 0;
        if (0 <= position && position <= 1) {
            alpha = 1 - position;
        } else if (-1 < position && position < 0) {
            alpha = position + 1;
        }
        view.setAlpha(alpha);
        view.setTranslationX(view.getWidth() * -position);
        float yPosition = position * view.getHeight();
        view.setTranslationY(yPosition);
    }
}
