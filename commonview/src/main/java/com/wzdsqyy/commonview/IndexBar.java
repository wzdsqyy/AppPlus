package com.wzdsqyy.commonview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;


/**
 * Created by YoKey on 16/10/6.
 */
public class IndexBar extends View {
    private int mTotalHeight;
    private List<String> mIndexList;
    private OnIndexTouchListener listener;
    private int mSelectionPosition;
    private float mIndexHeight;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int color = 0xff333333;
    private int focusColor = 0xffff3333;
    private float size = sp2px(12);
    private int focusSize = sp2px(13);
    private float textSpace = dp2px(8);

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public IndexBar setColor(@ColorInt int color) {
        this.color = color;
        mPaint.setColor(color);
        return this;
    }

    public IndexBar setFocusColor(@ColorInt int focusColor) {
        this.focusColor = focusColor;
        return this;
    }

    public IndexBar setFocusSize(float focusSize) {
        this.focusSize = sp2px(focusSize);
        return this;
    }

    public IndexBar setTextSpace(float textSpace) {
        this.textSpace = dp2px(textSpace);
        return this;
    }

    public IndexBar setSize(float size) {
        this.size = sp2px(size);
        mPaint.setTextSize(size);
        return this;
    }

    public IndexBar setOnIndexTouchListener(OnIndexTouchListener listener) {
        this.listener = listener;
        return this;
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value + 0.5f, getResources().getDisplayMetrics());
    }

    private int sp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value + 0.5f, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mIndexList.size() > 0) {
            mTotalHeight = (int) (((mIndexList.size() - 1) * mPaint.getTextSize() + mPaint.getTextSize())
                    + (mIndexList.size() + 1) * textSpace);
        }
        if (mTotalHeight > height) {
            mTotalHeight = height;
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mTotalHeight, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIndexList == null || mIndexList.size() == 0) {
            return;
        }
        mIndexHeight = ((float) getHeight()) / mIndexList.size();
        for (int i = 0; i < mIndexList.size(); i++) {
            if (mSelectionPosition == i) {
                mPaint.setColor(focusColor);
                mPaint.setTextSize(focusSize);
            } else {
                mPaint.setColor(color);
                mPaint.setTextSize(size);
            }
            canvas.drawText(mIndexList.get(i), getWidth() / 2, mIndexHeight * 0.85f + mIndexHeight * i, mPaint);
        }
    }

    private int getPositionForPointY(float y) {
        if (mIndexList.size() <= 0) return -1;

        int position = (int) (y / mIndexHeight);

        if (position < 0) {
            position = 0;
        } else if (position > mIndexList.size() - 1) {
            position = mIndexList.size() - 1;
        }
        return position;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchPos = getPositionForPointY(event.getY());
        if (touchPos < 0) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (listener != null) {
                    listener.onIndexTouch(this, true, touchPos);
                }
            case MotionEvent.ACTION_MOVE:
                if (touchPos == getSelect()) {
                    break;
                }
                setSelect(touchPos);
                if (listener != null) {
                    listener.onIndexScroll(this, getSelect());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (listener != null) {
                    listener.onIndexTouch(this, false, touchPos);
                }
                break;
        }
        return true;
    }

    public int getSelect() {
        return mSelectionPosition;
    }

    public void setSelect(int position) {
        this.mSelectionPosition = position;
        invalidate();
    }

    public IndexBar setIndexList(List<String> mlist) {
        this.mIndexList = mlist;
        invalidate();
        return this;
    }

    public List<String> getIndexList() {
        return mIndexList;
    }
}
