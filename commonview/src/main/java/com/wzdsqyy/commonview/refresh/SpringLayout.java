package com.wzdsqyy.commonview.refresh;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by liaoinstan on 2016/3/11.
 */
public class SpringLayout extends FrameLayout implements NestedScrollingParent {
    OverScroller mScroller;
    NestedScrollingParentHelper mParentHelper;
    int HEADER_MAX_DISTANCE = 350, FOOTER_MAX_DISTANCE = 350;
    int mHeaderDistance = 0, mFooterDistance = 0;

    public SpringLayout(Context context) {
        this(context, null);
    }

    public SpringLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mParentHelper = new NestedScrollingParentHelper(this);
        mScroller = new OverScroller(context);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mParentHelper.onStopNestedScroll(target);
        if (Math.abs(mHeaderDistance) > 0) {
            mScroller.startScroll(0, getScrollY(), 0, -mHeaderDistance);
            invalidate();
            mFooterDistance = 0;
            mHeaderDistance = 0;
        } else if (Math.abs(mFooterDistance) > 0) {
            mScroller.startScroll(0, getScrollY(), 0, -mFooterDistance);
            invalidate();
            mFooterDistance = 0;
            mHeaderDistance = 0;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
        if (!ViewCompat.canScrollVertically(target, -1)) {
            handleUpBoundary(dyUnconsumed);
        } else if (!ViewCompat.canScrollVertically(target, 1)) {
            handleDowmBoundary(dyUnconsumed);
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (!ViewCompat.canScrollVertically(target, -1)) {
            consumed[1] = handleUpBoundary(dy);
        } else if (!ViewCompat.canScrollVertically(target, 1)) {
            consumed[1] = handleDowmBoundary(dy);
        }
    }

    /**
     * 处理上边界的问题
     *
     * @param dy
     */
    int handleUpBoundary(int dy) {
        if(dy>0&&mHeaderDistance>=0){
            return 0;
        }
        int consumedY = dy;
        int distance = mHeaderDistance + dy;
        if (distance > 0) {
            consumedY = -mHeaderDistance;
        }else if (distance < -HEADER_MAX_DISTANCE) {
            distance = -HEADER_MAX_DISTANCE;
        }
        mHeaderDistance = distance;
        upBoundary(distance);
        return consumedY;
    }

    private void upBoundary(int distance) {
        scrollTo(0, distance);
    }

    private void downBoundary(int distance) {
        scrollTo(0, distance);
    }

    /**
     * 处理下边界的问题
     *
     * @param dy
     */
    int handleDowmBoundary(int dy) {
        if(dy<0&&mFooterDistance<=0){
            return 0;
        }
        int consumedY = dy;
        int distance = mFooterDistance + dy;
        if(distance<0){
            consumedY = -mFooterDistance;
        }
        if (distance > FOOTER_MAX_DISTANCE) {
            distance = FOOTER_MAX_DISTANCE;
        }
        mFooterDistance = distance;
        downBoundary(distance);
        return consumedY;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }
}
