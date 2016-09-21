package com.wzdsqyy.applib.utils;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Qiuyy on 2016/9/13.
 */
public class BadgeView extends FrameLayout{
    private TextView mBadgeView;
    private boolean mHideOnZero;
    private boolean mShowNum;

    public BadgeView(Context context) {
        this(context,true);
    }

    public BadgeView(Context context, boolean showNum) {
        super(context);
        mBadgeView = new TextView(context);
        this.mShowNum =showNum;
        addView(mBadgeView);
        initBadgeView(mBadgeView, showNum);
    }

    private void initBadgeView(TextView mBadgeView, boolean isShowNum) {
        int width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        int margin = 0;
        if (!isShowNum) {
            width = dip2Px(8);
            margin = dip2Px(3);
        }
        int height = width;
        if (!(getLayoutParams() instanceof LayoutParams)) {
            LayoutParams layoutParams =
                    new LayoutParams(height, height, Gravity.RIGHT | Gravity.TOP);
            if (!isShowNum) {
                layoutParams.rightMargin = margin;
                layoutParams.topMargin = margin;
                layoutParams.leftMargin = margin;
                layoutParams.bottomMargin = margin;
            }
            mBadgeView.setLayoutParams(layoutParams);
        }
        // set default font
        if (isShowNum) {
            mBadgeView.setTextColor(Color.WHITE);
            mBadgeView.setTypeface(Typeface.DEFAULT_BOLD);
            mBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            mBadgeView.setPadding(dip2Px(5), dip2Px(1), dip2Px(5), dip2Px(1));
            // set default background
            setBadgeViewBackground(mBadgeView, 9, Color.parseColor("#d3321b"));
            mBadgeView.setGravity(Gravity.CENTER);
        } else {
            setBadgeViewBackground(mBadgeView, 8, Color.parseColor("#d3321b"));
        }
        // default values
        setBadgeCount(0);
        mHideOnZero=true;
    }

    public void setHideOnZero(boolean HideOnZero) {
        mHideOnZero = HideOnZero;
        int count = TextUtils.isEmpty(mBadgeView.getText().toString()) ? 0 : Integer.parseInt(mBadgeView.getText().toString());
        setBadgeCount(count);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        mBadgeView.bringToFront();
    }

    public void setBadgeCount(int count) {
        if(count==0&&mHideOnZero||count<0){
            mBadgeView.setVisibility(GONE);
        }else{
            mBadgeView.setVisibility(VISIBLE);
        }
        if(mShowNum){
            mBadgeView.setText(count + "");
        }
    }

    private void setBadgeViewBackground(TextView mBadgeView, int dipRadius, int badgeColor) {
        int radius = dip2Px(dipRadius);
        float[] radiusArray = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape roundRect = new RoundRectShape(radiusArray, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
        bgDrawable.getPaint().setColor(badgeColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBadgeView.setBackground(bgDrawable);
        } else {
            mBadgeView.setBackgroundDrawable(bgDrawable);
        }
    }

    public void incrementBadgeCount(int increment) {
        Integer count = getBadgeCount();
        if (count == null) {
            setBadgeCount(increment);
        } else {
            setBadgeCount(increment + count);
        }
    }

    public void decrementBadgeCount(int decrement) {
        incrementBadgeCount(-decrement);
    }

    public void setBadgeMargin(int dipMargin) {
        setBadgeMargin(dipMargin, dipMargin, dipMargin, dipMargin);
    }

    public void setBadgeMargin(int leftDipMargin, int topDipMargin, int rightDipMargin, int bottomDipMargin) {
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.leftMargin = dip2Px(leftDipMargin);
        params.topMargin = dip2Px(topDipMargin);
        params.rightMargin = dip2Px(rightDipMargin);
        params.bottomMargin = dip2Px(bottomDipMargin);
        setLayoutParams(params);
    }

    private int dip2Px(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public int getBadgeCount() {
        if (TextUtils.isEmpty(mBadgeView.getText().toString())) {
            return 0;
        }
        String text = mBadgeView.getText().toString();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public BadgeView setTargetView(View target) {
        ViewUtils.toViewParent(this,target);
        return this;
    }
}

