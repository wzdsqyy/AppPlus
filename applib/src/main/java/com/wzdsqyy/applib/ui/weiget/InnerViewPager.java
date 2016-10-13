package com.wzdsqyy.applib.ui.weiget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/11/5.
 */
public class InnerViewPager extends ViewPager {
    public InnerViewPager(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    public InnerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    private GestureDetector mGestureDetector;

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            /**
             * 如果我们滚动更接近水平方向,返回false,让子视图来处理它
             */
//    		Log.v("XXX1",""+(Math.abs(distanceX) +":"+ Math.abs(distanceY)));
            return (Math.abs(distanceY) < Math.abs(distanceX));
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mGestureDetector.onTouchEvent(ev))
            getParent().requestDisallowInterceptTouchEvent(true);////这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。
        return super.dispatchTouchEvent(ev);
    }
}
