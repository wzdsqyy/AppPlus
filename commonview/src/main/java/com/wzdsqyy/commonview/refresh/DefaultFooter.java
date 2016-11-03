package com.wzdsqyy.commonview.refresh;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;


/**
 * Created by Administrator on 2016/3/21.
 */
public class DefaultFooter extends ProgressBar implements UIHandler {


    public DefaultFooter(Context context) {
        super(context);
    }

    @Override
    public int getDragLimitHeight(View rootView) {
        return 0;
    }

    @Override
    public int getDragMaxHeight(View rootView) {
        return 0;
    }

    @Override
    public int getDragSpringHeight(View rootView) {
        return 0;
    }

    @Override
    public void onPreDrag(View rootView) {
    }

    @Override
    public void onMove(View rootView, int dy) {
    }

    @Override
    public void onLimitDes(View rootView, boolean isUp) {
//        if (upORdown) {
//            footerTitle.setText("松开载入更多");
//        } else {
//            footerTitle.setText("查看更多");
//        }
    }

    @Override
    public void onStartAnim() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishAnim() {
        setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}