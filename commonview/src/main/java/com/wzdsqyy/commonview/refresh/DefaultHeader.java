package com.wzdsqyy.commonview.refresh;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;


/**
 * Created by Administrator on 2016/3/21.
 */
public class DefaultHeader extends ProgressBar implements UIHandler {

    public DefaultHeader(Context context) {
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
    public void onMove(View refresh, int dy) {
    }


    @Override
    public void onLimitDes(View rootView, boolean isUp) {
        if (!isUp){
//            headerTitle.setText("松开刷新数据");
//            if (headerArrow.getVisibility()== View.VISIBLE)
//                headerArrow.startAnimation(mRotateUpAnim);
        }
        else {
//            headerTitle.setText("下拉刷新");
//            if (headerArrow.getVisibility()== View.VISIBLE)
//                headerArrow.startAnimation(mRotateDownAnim);
        }
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
        return true;
    }
}