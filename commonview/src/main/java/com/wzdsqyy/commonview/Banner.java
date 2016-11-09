package com.wzdsqyy.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.OverScroller;

/**
 * 无限循环的三个ImageView用于轮播图的展示
 */

public class Banner extends FrameLayout {

    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    private int count = -1;
    private OverScroller mScroller;
    private BannerListener listener;
    private int mLastX;
    private ImageView mIvLeft, mIvRight, mIvCenter;
    private int mCenter = 0;
    private int mStatus = -1;
    private boolean mFristInit = false;
    private int duration = 700;//滑动时手指释放后执行的动画时间
    private int mduration = 800;//下一页，前一页动画时长

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
        mIvLeft = new ImageView(context);
        mIvRight = new ImageView(context);
        mIvCenter = new ImageView(context);
        if (isInEditMode()) {
            return;
        }
        addViewInLayout(mIvLeft, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addViewInLayout(mIvCenter, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addViewInLayout(mIvRight, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) { // 如果上次的调用没有执行完就取消。
                    mScroller.abortAnimation();
                }
                mLastX = x;
                return true;
            case MotionEvent.ACTION_MOVE:
                int dxMove = (mLastX - x);
                if (listener != null && mStatus != SCROLL_STATE_DRAGGING) {
                    listener.onPageScrollStateChanged(SCROLL_STATE_DRAGGING);
                    mStatus = SCROLL_STATE_DRAGGING;
                }
                scrollTo(getScrollX() + dxMove, 0);
                mLastX = x;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (listener != null && mStatus != SCROLL_STATE_IDLE) {
                    listener.onPageScrollStateChanged(SCROLL_STATE_IDLE);
                    mStatus = SCROLL_STATE_IDLE;
                }
                int move;
                if (this.getScrollX() > getWidth() / 2) {
                    move = getWidth() - this.getScrollX();
                } else {
                    move = -this.getScrollX();
                }
                mScroller.startScroll(this.getScrollX(), 0, move, 0, duration);
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    public Banner setBannerListener(BannerListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            invalidate();
        }
    }

    public Banner setDuration(int duration) {
        this.mduration = duration;
        return this;
    }

    public void showNext() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        scrollTo(0, 0);
        mScroller.startScroll(0, 0, getWidth(), 0, mduration);
        invalidate();
    }

    public void showPrevious() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        scrollTo(getWidth(), 0);
        mScroller.startScroll(getWidth(), 0, -getWidth(), 0, mduration);
        invalidate();
    }

    public Banner setCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("必须大于0");
        }
        this.count = count;
        return this;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!changed) {
            return;
        }
        l = l - getScrollX();
        r = r - getScrollX();
        mIvLeft.layout(l - getMeasuredWidth(), t, r - getMeasuredWidth(), b);
        mIvCenter.layout(l, t, r, b);
        mIvRight.layout(l + getMeasuredWidth(), t, r + getMeasuredWidth(), b);
        if (mFristInit||count<=0) {
            return;
        }
        if (listener != null) {
            int right = (mCenter + 1) % count;//右边的索引
            int left = (count - 1) % count;
            left=left>0?left:count+left;
            listener.onPageSelected(mCenter, mIvCenter);
            listener.onPageSelected(right, mIvRight);
            listener.onPageSelected(left, mIvLeft);
            mFristInit = true;
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (getWidth() <= 0 || count < 1) {
            super.scrollTo(x, y);
            return;
        }
        int cemter = mCenter;
//        if(getScrollX()==getWidth()){
//            cemter++;
//        }
//
//
//
//        if (x < 0) {
//            x = x + getWidth();
//            left--;
//        } else if (x > getWidth()) {
//            x = x - getWidth();
//            left++;
//        }
//        if (left < 0) {
//            left = count + left % count;
//        }
//        if (left > count) {
//            left = left % count;
//        }
//        int right = (left + 1) % count;//右边的索引
//        if (left != mCenter) {
//            mCenter = left;
//            if (listener != null) {
//                listener.onPageSelected(left, mIvLeft);
//                listener.onPageSelected(right, mIvRight);
//            }
//        }
//        if (listener != null) {
//            float radio = x / (float) getWidth();
//            listener.onPageScrolled(right, radio, mIvRight);
//            listener.onPageScrolled(left, (1 - radio), mIvLeft);
//        }
        super.scrollTo(x, y);
    }

    public static interface BannerListener {

        void onPageScrolled(int position, float radio, ImageView imageView);

        void onPageSelected(int position, ImageView view);

        void onPageScrollStateChanged(int state);
    }
}
