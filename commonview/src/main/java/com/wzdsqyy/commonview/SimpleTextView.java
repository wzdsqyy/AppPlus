package com.wzdsqyy.commonview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * Created by Administrator on 2016/11/15.
 */

public class SimpleTextView extends View {
    private Paint mPaint;
    private Drawable left, right;
    private int textColor;
    private float textSize;
    private String mText;
    private Gravity gravity;
    private boolean isDraw = false;

    public SimpleTextView(Context context) {
        this(context, null);
    }

    public SimpleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public SimpleTextView setTextColor(@ColorInt int textColor) {
        if (this.textColor == textColor) {
            return this;
        }
        this.textColor = textColor;
        mPaint.setColor(textColor);
        if (isDraw) {
            invalidate();
        }
        return this;
    }

    public SimpleTextView setGravity(Gravity gravity) {
        if (this.gravity == gravity) {
            return this;
        }
        this.gravity = gravity;
        if (isDraw) {
            invalidate();
        }
        return this;
    }

    public SimpleTextView setTextSize(float textSize) {
        if (this.textSize == textSize) {
            return this;
        }
        this.textSize = textSize;
        mPaint.setTextSize(textSize);
        if (isDraw) {
            invalidate();
        }
        return this;
    }

    public SimpleTextView setLeft(Drawable left) {
        if (this.left == left) {
            return this;
        }
        this.left = left;
        if (isDraw) {
            invalidate();
        }
        return this;
    }

    public SimpleTextView setText(String text) {
        if (TextUtils.equals(text, this.mText)) {
            return this;
        }
        this.mText = text;
        if (isDraw) {
            invalidate();
        }
        return this;
    }

    public SimpleTextView setRight(Drawable right) {
        if (this.right == right) {
            return this;
        }
        this.right = right;
        if (isDraw) {
            invalidate();
        }
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        isDraw = true;
    }
}
