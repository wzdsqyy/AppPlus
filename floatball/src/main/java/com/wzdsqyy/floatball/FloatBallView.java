package com.wzdsqyy.floatball;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * Created by wangxiandeng on 2016/11/25.
 */

public class FloatBallView extends View implements GestureDetector.OnGestureListener {
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private float mTouchSlop;
    private int mCurrentMode;
    private final static int MODE_DOWN = 0x001;
    private final static int MODE_UP = 0x002;
    private final static int MODE_LEFT = 0x003;
    private final static int MODE_RIGHT = 0x004;
    private final static int MODE_GONE = 0x006;
    private Vibrator mVibrator;
    private long[] mPattern = {0, 100};
    private boolean isMoveing = false;
    private float offsetX;
    private float offsetY;

    public FloatBallView setMoveing(boolean moveing) {
        isMoveing = moveing;
        if (isMoveing) {
            setBackgroundResource(R.drawable.bg_ball_move);
        }
        return this;
    }


    private GestureDetector detector;


    private int mDownX, mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                offsetX = getWindowLayoutParams().x - event.getRawX();
                offsetY = getWindowLayoutParams().y - event.getRawY();
                mDownX = (int) event.getRawX();
                mDownY = (int) event.getRawY();
                setBackgroundResource(R.drawable.bg_ball_click);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchSlop(event)) {
                    return false;
                } else {
                    updataViewLocation(event);
                    doGesture(event);
                    return true;
                }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                final int startX = (int) event.getRawX();
                final int startY = (int) event.getRawY();
                ValueAnimator animator = ValueAnimator.ofFloat(1f, 0);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        getWindowLayoutParams().x = (int) ((int) ((startX - mDownX) * valueAnimator.getAnimatedFraction() + mDownX) + offsetX);
                        getWindowLayoutParams().y = (int) ((int) ((startY - mDownY) * valueAnimator.getAnimatedFraction() + mDownY) + offsetY);
                        getWindowManager().updateViewLayout(FloatBallView.this, getWindowLayoutParams());
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        doUp();
                    }
                });
                animator.start();
                setBackgroundResource(R.drawable.bg_ball);
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void updataViewLocation(MotionEvent event) {
        getWindowLayoutParams().x = (int) ((int) event.getRawX() + offsetX);
        getWindowLayoutParams().y = (int) ((int) event.getRawY() + offsetY);
        getWindowManager().updateViewLayout(FloatBallView.this, getWindowLayoutParams());
    }

    public FloatBallView(Context context) {
        super(context);
        mVibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setBackgroundResource(R.drawable.bg_ball);
        detector = new GestureDetector(context, this);
    }

    public WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    public WindowManager.LayoutParams getWindowLayoutParams() {
        if (mLayoutParams == null) {
            mLayoutParams = (WindowManager.LayoutParams) getLayoutParams();
        }
        return mLayoutParams;
    }


    /**
     * 移除悬浮球
     */
    private void toRemove() {
        mVibrator.vibrate(mPattern, -1);
        getContext().stopService(new Intent(getContext(), FloatBallService.class));
    }

    /**
     * 判断是否是轻微滑动
     *
     * @param event
     * @return
     */
    private boolean isTouchSlop(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (Math.abs(x - mDownX) < mTouchSlop && Math.abs(y - mDownY) < mTouchSlop) {
            return true;
        }
        return false;
    }

    /**
     * 判断手势（左右滑动、上拉下拉)）
     *
     * @param event
     */
    private boolean doGesture(MotionEvent event) {
        float offsetX = event.getX() - mDownX;
        float offsetY = event.getY() - mDownY;
        if (Math.abs(offsetX) > Math.abs(offsetY)) {
            if (offsetX > 0) {
                if (mCurrentMode == MODE_RIGHT) {
                    return true;
                }
                mCurrentMode = MODE_RIGHT;
            } else {
                if (mCurrentMode == MODE_LEFT) {
                    return true;
                }
                mCurrentMode = MODE_LEFT;
            }
        } else {
            if (offsetY > 0) {
                if (mCurrentMode == MODE_DOWN || mCurrentMode == MODE_GONE) {
                    return true;
                }
                mCurrentMode = MODE_DOWN;
            } else {
                if (mCurrentMode == MODE_UP) {
                    return true;
                }
                mCurrentMode = MODE_UP;
            }
        }
        return false;
    }

    /**
     * 手指抬起后，根据当前模式触发对应功能
     */
    private void doUp() {
        switch (mCurrentMode) {
            case MODE_LEFT:
            case MODE_RIGHT:
                FloatBallService.action(getContext(), FloatBallService.ACTION_RECENTS);
                break;
            case MODE_DOWN:
                FloatBallService.action(getContext(), FloatBallService.ACTION_NOTIFICATIONS);
                break;
            case MODE_UP:
                FloatBallService.action(getContext(), FloatBallService.ACTION_HOME);
                break;

        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

//    /**
//     * 判断是否是长按
//     *
//     * @param event
//     * @return
//     */
//    private boolean isLongClick(MotionEvent event) {
//        float offsetX = Math.abs(event.getX() - mLastDownX);
//        float offsetY = Math.abs(event.getY() - mLastDownY);
//        long time = System.currentTimeMillis() - mLastDownTime;
//
//        if (offsetX < mTouchSlop && offsetY < mTouchSlop && time >= LONG_CLICK_LIMIT) {
//            //震动提醒
//            mVibrator.vibrate(mPattern, -1);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 判断是否是单击
//     *
//     * @param event
//     * @return
//     */
//    private boolean isClick(MotionEvent event) {
//        float offsetX = Math.abs(event.getX() - mLastDownX);
//        float offsetY = Math.abs(event.getY() - mLastDownY);
//        long time = System.currentTimeMillis() - mLastDownTime;
//
//        if (offsetX < mTouchSlop * 2 && offsetY < mTouchSlop * 2 && time < CLICK_LIMIT) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 获取通知栏高度
//     *
//     * @return
//     */
//    private int getStatusBarHeight() {
//        int statusBarHeight = 0;
//        try {
//            Class<?> c = Class.forName("com.android.internal.R$dimen");
//            Object o = c.newInstance();
//            Field field = c.getField("status_bar_height");
//            int x = (Integer) field.get(o);
//            statusBarHeight = getResources().getDimensionPixelSize(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return statusBarHeight;
//    }
//
//    public int dip2px(float dip) {
//        return (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, dip, getContext().getResources().getDisplayMetrics()
//        );
//    }

}
