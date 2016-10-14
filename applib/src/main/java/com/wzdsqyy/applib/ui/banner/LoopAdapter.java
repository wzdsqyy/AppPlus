package com.wzdsqyy.applib.ui.banner;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class LoopAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private static final String TAG = "LoopAdapter";
    private ViewPager mPager;
    private List<ViewPager.OnPageChangeListener> mOnPageChangeListeners;
    private long last;
    private long nextPosition;

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    /**
     * Remove a listener that was previously added via
     * {@link #addOnPageChangeListener(ViewPager.OnPageChangeListener)}.
     *
     * @param listener listener to remove
     */
    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }

    public LoopAdapter setupPager(@NonNull ViewPager mPager) {
        this.mPager = mPager;
        this.mPager.addOnPageChangeListener(this);
        this.mPager.clearOnPageChangeListeners();
        return this;
    }

    @Override
    public final int getCount() {
        return getRealCount() + 2;
    }

    public abstract int getRealCount();

    /**
     * @param position
     * @return
     */
    private int getRealPosition(int position) {
        if (position == getCount() - 1) {//ViewPager内部的最后一个
            return 1;
        }
        if (position == 0) {//ViewPager内部的第一个
            return getCount() - 2;
        }
        return position;//ViewPager内部
    }

    public void setCurrentItem(int position) {
        setCurrentItem(position, position != 0);
    }

    public void setCurrentItem(int position, boolean smoothScroll) {
        check();
        mPager.setCurrentItem(position + 1, smoothScroll);
    }

    private void check() {
        if (mPager == null) {
            throw new RuntimeException("必须提前调用setupPager()方法");
        }
    }

    public abstract View getView(ViewGroup container, int position);

    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        View view = getView(container, getRealPosition(position) - 1);
        container.addView(view);
        return view;
    }

    public int getCurrentItem() {
        return getRealPosition(mPager.getCurrentItem()) - 1;
    }

    @Override
    public final void startUpdate(ViewGroup container) {
        if(mPager!=null){
            int position = mPager.getCurrentItem();
            if (isVirtualPosition(position)) {
                mPager.setCurrentItem(getRealPosition(position), false);
            }
        }
    }

    @Override
    public final void finishUpdate(ViewGroup container) {
//        if (mPager != null) {
//            int position = mPager.getCurrentItem();
//            if (isVirtualPosition(position)) {
//                mPager.setCurrentItem(getRealPosition(position), false);
//            }
//        }
    }

    private boolean isVirtualPosition(int position) {
        return position == 0 || position == getCount() - 1;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (isVirtualPosition(position)) {
            return;
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                mOnPageChangeListeners.get(i).onPageScrolled(getRealPosition(position) - 1, positionOffset, positionOffsetPixels);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (isVirtualPosition(position)) {
            return;
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                mOnPageChangeListeners.get(i).onPageSelected(getRealPosition(position) - 1);
            }
        }
    }

    public void switchToNextPage() {
        check();
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }

    public void switchToPrePage() {
        check();
        mPager.setCurrentItem(mPager.getCurrentItem()-1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListeners != null) {
            for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                mOnPageChangeListeners.get(i).onPageScrollStateChanged(state);
            }
        }
    }
}
