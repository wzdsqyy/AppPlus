package com.wzdsqyy.applibDemo.lrulist;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/10/27.
 */

public class ViewWapper extends View {
    private View target;
    public ViewWapper(Context context) {
        super(context);
    }

    public ViewWapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewWapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        target.measure(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);















    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(target!=null){
            target.draw(canvas);
        }
    }
}
