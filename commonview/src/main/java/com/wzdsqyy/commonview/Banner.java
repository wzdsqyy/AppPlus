//package com.wzdsqyy.commonview;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.OverScroller;
//
///**
// * Created by Administrator on 2016/11/7.
// */
//
//public class Banner extends FrameLayout {
//
//    private int offset = 0;//大于0小于此View的宽
//    private int count = 1;
//    private int possion = 0;
//    private OverScroller mScroller;
//    private BannerListener listener;
//    private int mLastX;
//    private ImageView mOther, mIndex;
//    private long maxDistance;
//    private long distance;
//
//    public Banner(Context context) {
//        this(context, null);
//    }
//
//    public Banner(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mScroller = new OverScroller(context);
//        mOther = new ImageView(context);
//        mIndex = new ImageView(context);
//        addView(mIndex, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        addView(mOther, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getRawX();
//        int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                if (!mScroller.isFinished()) { // 如果上次的调用没有执行完就取消。
//                    mScroller.abortAnimation();
//                }
//                mLastX = x;
//                offset = 0;
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                int dxMove = (mLastX - x);
//                if (listener != null) {
//                    listener.onPageScrollStateChanged(BannerListener.SCROLL_STATE_DRAGGING);
//                }
//                scrollView(dxMove, 0);
//                invalidate();
//                mLastX = x;
//                return true;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                if (listener != null) {
//                    listener.onPageScrollStateChanged(BannerListener.SCROLL_STATE_IDLE);
//                }
//                int sonIndex = (getScrollX() + getWidth() / 2) / getWidth();
//
//                // 如果滑动超过最后一页，就退回到最后一页。
//                int childCount = getChildCount();
//                if (sonIndex >= childCount)
//                    sonIndex = childCount - 1;
//                // 现在滑动的相对距离。
//                int dx = sonIndex * getWidth() - getScrollX();
//                // Y方向不变，X方向到目的地。
//                mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
//                invalidate();
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    public Banner setBannerListener(BannerListener listener) {
//        this.listener = listener;
//        return this;
//    }
//
//    private void scrollView(int dx, int dy) {
//        if (getWidth() == 0 || maxDistance == 0) {
//            return;
//        }
//        distance = distance + dx;
//        if (distance > maxDistance) {
//            distance = distance % maxDistance;
//        }
//        int index = (int) (distance / getWidth()) - 1;
//        if (listener != null && possion != index) {
//            listener.onPageSelected(index,mIndex);
//        }
//        possion = index;
//
//        offset = (int) distance % getWidth();
//    }
//
//    @Override
//    public void computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            scrollView(mScroller.getCurrX(), mScroller.getCurrY());
//            invalidate();
//        }
//    }
//
//    public Banner setCount(int count) {
//        if (count <= 0) {
//            throw new IllegalArgumentException("必须大于0");
//        }
//        this.count = count;
//        this.maxDistance = getWidth() * count;
//        return this;
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        maxDistance = getMeasuredWidth() * count;
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        if (!changed) {
//            return;
//        }
//        mIndex.layout(l + offset, t, r + offset, b);
//        mOther.layout(getOtherLeft(l), t, r + offset + getWidth(), b);
//    }
//
//    private int updateoffset(int offset,int dx) {
//        if(getWidth()==0||count<1){
//            return offset;
//        }
//        int other;
//        if(dx>0){
//            other=possion+1;
//        }else{
//            other=possion-1;
//        }
//
//
//
//
//
//
//        if(listener!=null){
//            listener.onPageSelected(possion,mIndex);
//            listener.onPageSelected(other,mOther);
//        }
//
//
//
//
//
//
//        offset=offset+dx;
//        if(offset<0){
//            offset=offset+getWidth();
//            if(possion==0)
//            if(listener!=null){
//                listener.onPageSelected(possion);
//            }
//        }
//
//
//
//
//        if(getWidth()!=0){
//            if(offset>getWidth()){
//                offset=offset%getWidth();
//            }
//        }
//        return offset;
//    }
//}
