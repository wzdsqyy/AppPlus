package com.wzdsqyy.commonview;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by Qiuyy on 2016/9/13.
 */
public class BadgeDrawable extends Drawable {
    private Drawable drawable;
    private Paint mPaint;
    private boolean mHideOnZero = true;
    private boolean mShowNum = false;
    private boolean centerHorizontal = false;
    private boolean centerVertical = false;
    private String mNumCount;
    @ColorInt
    private int badgeColor = Color.RED;
    @ColorInt
    private int mNumColor = Color.WHITE;
    private int mNumSize = 10;
    private int mStrokeWidth = 1;
    @ColorInt
    private int mStrokeColor = Color.BLACK;
    private int radius = 4;
    private int mLeft = 0;
    private int mTop = 0;
    private float mTextWidth;
    private float mTextHeight;
    private boolean showStroke=false;

    public BadgeDrawable(@NonNull Drawable drawable) {
        this.drawable = drawable;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStrokeWidth(1);
    }

    public BadgeDrawable setStrokeColor(int mStrokeColor) {
        this.mStrokeColor = mStrokeColor;
        return this;
    }

    public BadgeDrawable setStrokeWidth(@ColorInt int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        return this;
    }

    public BadgeDrawable setShowNum(boolean show) {
        if (this.mShowNum != show) {
            this.mShowNum = show;
        }
        return this;
    }

    public BadgeDrawable setCenterHorizontal(boolean centerHorizontal) {
        this.centerHorizontal = centerHorizontal;
        if (centerHorizontal) {
            mLeft = 0;
            mTop = 0;
        }
        return this;
    }

    public BadgeDrawable setCenterVertical(boolean centerVertical) {
        this.centerVertical = centerVertical;
        if (centerVertical) {
            mLeft = 0;
            mTop = 0;
        }
        return this;
    }

    public void setBadgeCount(int count) {
        String temp = count + "";
        if (count <= 0) {
            temp = "";
        } else if (count > 99) {
            temp = "99+";
        }
        if (!TextUtils.equals(temp, mNumCount)) {
            mNumCount = temp;
            measureNumCount(mNumCount);

        }
    }

    private void measureNumCount(String text) {
        if (!TextUtils.isEmpty(text)) {
            Rect mBounds = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), mBounds);
            mTextHeight = mBounds.height();
            mTextWidth = mPaint.measureText(text);
        }
    }

    public void setBadgeColor(@ColorInt int badgeColor) {
        if (this.badgeColor != badgeColor) {
            this.badgeColor = badgeColor;
        }
    }

    public void setNumSize(float size) {
        if (mPaint.getTextSize() != size) {
            mPaint.setTextSize(size);
            measureNumCount(mNumCount + "");
        }
    }

    public void setRadius(int px) {
        if (this.radius != px) {
            this.radius = px;
        }
    }

    public BadgeDrawable setLeft(int left) {
        if (mLeft != left) {
            mLeft = left;
            setCenterHorizontal(false);
        }
        return this;
    }

    public BadgeDrawable setTop(int top) {
        if (mTop != top) {
            mTop = top;
            setCenterHorizontal(false);
        }
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
        canvas.save();
        mPaint.setColor(badgeColor);
        float dotR = radius;
        if (mShowNum) {
            dotR = (mTextHeight > mTextWidth ? mTextHeight + radius : mTextWidth + radius) / 2;
            if(showStroke){
                dotR=dotR+mStrokeWidth;
            }
        }
        float centerDotX = mLeft + dotR;
        float centerDotY = mTop + dotR;
        if (mLeft < 0) {
            centerDotX = canvas.getWidth() + mLeft - dotR;
        }
        if (mTop < 0) {
            centerDotY = canvas.getHeight() + mTop - dotR;
        }
        if (centerHorizontal) {
            centerDotX = canvas.getWidth() / 2;
        }
        if (centerVertical) {
            centerDotY = canvas.getHeight() / 2;
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerDotX, centerDotY, dotR, mPaint);
        if(showStroke){
            mPaint.setColor(mStrokeColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
            canvas.drawCircle(centerDotX,centerDotY,dotR,mPaint);
        }
        if (mShowNum && !TextUtils.isEmpty(mNumCount)) {
            mPaint.setColor(mNumColor);
            float textY = centerDotY + mTextHeight / 2f;
            float textX = centerDotX - (mTextWidth / 2f);
            canvas.drawText(mNumCount + "", textX, textY, mPaint);
        }
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {
        if (drawable == null) {
            return;
        }
        drawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (drawable == null) {
            return;
        }
        drawable.setColorFilter(colorFilter);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override
    public int getOpacity() {
        if (drawable == null) {
            return PixelFormat.OPAQUE;
        }
        return drawable.getOpacity();
    }
}

