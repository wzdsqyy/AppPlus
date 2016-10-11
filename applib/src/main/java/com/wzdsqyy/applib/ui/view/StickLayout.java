package com.wzdsqyy.applib.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/9/30.
 */

public class StickLayout extends FrameLayout{
    private View stickView;

    public StickLayout(Context context) {
        this(context,null);
    }

    public StickLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StickLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        stickView=new View(context);
    }

    public void setTarget(View target) {
        Bitmap cache = target.getDrawingCache();
    }
}
