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
    private OnRefreshListener refreshListener;
    private View target;
    private UIHandler mHeader;
    private UIHandler mFooter;

    @IntDef({IDLE, PULL_LOAD, PULL_REFRESH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }
    private static final int IDLE = 0;
    private static final int PULL_LOAD = 1;
    private static final int PULL_REFRESH = 2;
    private int status = IDLE;


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
            handleUpBoundary(dyUnconsumed,true);
        } else if (!ViewCompat.canScrollVertically(target, 1)) {
            handleDowmBoundary(dyUnconsumed,true);
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (!ViewCompat.canScrollVertically(target, -1)) {
            consumed[1] = handleUpBoundary(dy,true);
        } else if (!ViewCompat.canScrollVertically(target, 1)) {
            consumed[1] = handleDowmBoundary(dy,true);
        }
    }

    /**
     * 处理上边界的问题
     *
     * @param dy
     */
    int handleUpBoundary(float dy, boolean isTouch) {
        if (dy > 0 && mHeaderDistance >= 0) {
            return 0;
        }
        float consumedY = dy;
        float distance = mHeaderDistance + dy;
        if (distance > 0) {
            consumedY = -mHeaderDistance;
        } else if (distance < -HEADER_MAX_DISTANCE) {
            distance = -HEADER_MAX_DISTANCE;
        }
        mHeaderDistance = (int) distance;
        if (mHeader != null) {
            if (mHeader.onMove(this, dy, mHeaderDistance, isTouch) && refreshListener != null) {
                refreshListener.onRefresh();
            }
            distance = mHeader.moveView(mHeaderDistance);
            upBoundary(distance);
        }else {
            upBoundary(distance);
        }
        return (int) consumedY;
    }

    private void upBoundary(float distance) {
        scrollTo(0, (int) distance);
    }

    private void downBoundary(float distance) {
        scrollTo(0, (int) distance);
    }

    /**
     * 处理下边界的问题
     *
     * @param dy
     */
    int handleDowmBoundary(float dy, boolean isTouch) {
        if (dy < 0 && mFooterDistance <= 0) {
            return 0;
        }
        float consumedY = dy;
        float distance = mFooterDistance + dy;
        if (distance < 0) {
            consumedY = -mFooterDistance;
        }
        if (distance > FOOTER_MAX_DISTANCE) {
            distance = FOOTER_MAX_DISTANCE;
        }
        if (mFooter != null) {
            if (mFooter.onMove(this, dy, mFooterDistance, isTouch) && refreshListener != null) {
                refreshListener.onRefresh();
            }
            distance = mFooter.moveView(mHeaderDistance);
            downBoundary(distance);
        }else {
            downBoundary(distance);
        }

        return (int) consumedY;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        target = getChildAt(0);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int dy = mScroller.getCurrY() - mScroller.getCurrY();
            if(status==PULL_LOAD){
                handleDowmBoundary(dy,false);
            }else if(status==PULL_REFRESH){
                handleUpBoundary(dy,false);
            }
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
