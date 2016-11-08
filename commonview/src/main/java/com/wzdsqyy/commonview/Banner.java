package com.wzdsqyy.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.OverScroller;

/**
 * 无限循环的俩个ImageView用于轮播图的展示
 */

public class Banner extends FrameLayout {

    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    private static final String TAG = "Banner";
    private int offset = 0;//大于0小于此View的宽
    private int count = -1;
    private OverScroller mScroller;
    private BannerListener listener;
    private int mLastX;
    private ImageView mIvLeft, mIvRight;
    private int mLeft = 0;
    private int mStatus = -1;
    private boolean mFristInit = false;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
        mIvLeft = new ImageView(context);
        mIvRight = new ImageView(context);
        addViewInLayout(mIvLeft, -1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
                this.offset = updateoffset(dxMove);
                scrollTo(this.offset, 0);
                mLastX = x;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (listener != null && mStatus != SCROLL_STATE_IDLE) {
                    listener.onPageScrollStateChanged(SCROLL_STATE_IDLE);
                    mStatus = SCROLL_STATE_IDLE;
                }
                int move;
                if (this.offset > getWidth() / 2) {
                    move = getWidth() - this.offset;
                } else {
                    move = -this.offset;
                }
                mScroller.startScroll(getScrollX(), 0, move, 0, 700);
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
        l = l - offset;
        r = r - offset;
        mIvLeft.layout(l, t, r, b);
        mIvRight.layout(l + getMeasuredWidth(), t, r + getMeasuredWidth(), b);
        if (mFristInit) {
            return;
        }
        if (listener != null) {
            int right = (mLeft + 1) % count;//右边的索引
            listener.onPageSelected(mLeft, mIvLeft);
            listener.onPageSelected(right, mIvRight);
            mFristInit = true;
        }
    }

    private int updateoffset(int dx) {
        if (getMeasuredWidth() == 0 || count < 1) {
            return 0;
        }
        int offset = getScrollX() + dx;
        int left = mLeft;
        while (offset < 0) {
            offset = offset + getMeasuredWidth();
            left--;
        }
        while (offset >= getMeasuredWidth()) {
            offset = offset - getMeasuredWidth();
            left++;
        }
        if (left < 0) {
            left = left % count + count;
        }
        if (left >= count) {
            left = left % count;
        }
        int right = (left + 1) % count;//右边的索引
        if (left != mLeft) {
            mLeft = left;
            if (listener != null) {
                listener.onPageSelected(left, mIvLeft);
                listener.onPageSelected(right, mIvRight);
            }
        }
        if (listener != null) {
            float radio = offset / (float) getMeasuredWidth();
            listener.onPageScrolled(right, radio, mIvRight);
            listener.onPageScrolled(left, (1 - radio), mIvLeft);
        }
        return offset;
    }

    public static interface BannerListener {

        void onPageScrolled(int position, float radio, ImageView imageView);

        void onPageSelected(int position, ImageView view);

        void onPageScrollStateChanged(int state);
    }
}
