//package com.wzdsqyy.stickyheader;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.support.annotation.ColorInt;
//import android.support.annotation.DimenRes;
//import android.support.annotation.FloatRange;
//import android.util.TypedValue;
//import android.view.MotionEvent;
//import android.view.View;
//
//import java.util.List;
//
//import static android.R.attr.value;
//
//
///**
// * Created by YoKey on 16/10/6.
// */
//public class IndexBar extends View {
//    private int mTotalHeight;
//    private List<String> mIndexList;
//    private OnIndexTouchListener listener;
//
//    private int mSelectionPosition;
//    private float mIndexHeight;
//
//    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//    private int color=0xff333333;
//    private int focusColor=0xffff3333;
//    private float size=sp2px(12);
//    private int focusSize=sp2px(13);
//    private float textSpace=dp2px(8);
//    public IndexBar(Context context) {
//        super(context);
//        mPaint.setColor(color);
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        mPaint.setTextSize(size);
//    }
//
//    public void init(Drawable barBg, @ColorInt int color, @ColorInt int focusColor,float size, int focusSize, float textSpace) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            setBackground(barBg);
//        } else {
//            setBackgroundDrawable(barBg);
//        }
//        this.color=color;
//        this.focusColor=focusColor;
//        this.size=sp2px(size);
//        this.focusSize=sp2px(focusSize);
//        this.textSpace=dp2px(textSpace);
//        mPaint.setColor(color);
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        mPaint.setTextSize(size);
//    }
//
//    private int dp2px(float value) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value+0.5f, getResources().getDisplayMetrics());
//    }
//
//    private int sp2px(float value) {
//        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value+0.5f, getResources().getDisplayMetrics());
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        if (mIndexList.size() > 0) {
//            mTotalHeight = (int) (((mIndexList.size() - 1) * mPaint.getTextSize()+ mPaint.getTextSize())
//                    + (mIndexList.size() + 1) * textSpace);
//        }
//        if (mTotalHeight > height) {
//            mTotalHeight = height;
//        }
//        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mTotalHeight, MeasureSpec.EXACTLY));
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (mIndexList == null || mIndexList.size() == 0) {
//            return;
//        }
//        mIndexHeight = ((float) getHeight()) / mIndexList.size();
//        for (int i = 0; i < mIndexList.size(); i++) {
//            if(mSelectionPosition == i){
//                mPaint.setColor(focusColor);
//                mPaint.setTextSize(focusSize);
//            }else {
//                mPaint.setColor(color);
//                mPaint.setTextSize(size);
//            }
//            canvas.drawText(mIndexList.get(i), getWidth() / 2, mIndexHeight * 0.85f + mIndexHeight * i, mPaint);
//        }
//    }
//
//    private int getPositionForPointY(float y) {
//        if (mIndexList.size() <= 0) return -1;
//
//        int position = (int) (y / mIndexHeight);
//
//        if (position < 0) {
//            position = 0;
//        } else if (position > mIndexList.size() - 1) {
//            position = mIndexList.size() - 1;
//        }
//        return position;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int touchPos = getPositionForPointY(event.getY());
//        if (touchPos < 0) {
//            return true;
//        }
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if(listener!=null){
//                    listener.onIndexTouch(this,true);
//                }
//            case MotionEvent.ACTION_MOVE:
//                if(touchPos== getSelect()){
//                    break;
//                }
//                if(listener!=null){
//                    listener.onIndexScroll(this,getSelect());
//                }
//                setSelect(touchPos);
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                if(listener!=null){
//                    listener.onIndexTouch(this,false);
//                }
//                break;
//        }
//        return true;
//    }
//
//    public int getSelect() {
//        return mSelectionPosition;
//    }
//
//    public void setSelect(int position) {
//        this.mSelectionPosition = position;
//        invalidate();
//    }
//
//
//    public IndexBar setIndexList(List<String> mlist) {
//        this.mIndexList = mlist;
//        invalidate();
//        return this;
//    }
//
//    public List<String> getIndexList() {
//        return mIndexList;
//    }
//}
