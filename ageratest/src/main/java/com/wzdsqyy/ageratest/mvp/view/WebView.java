package com.wzdsqyy.ageratest.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;

/**
 * Created by Administrator on 2016/10/27.
 */

public class WebView extends android.webkit.WebView {
    public WebView(Context context) {
        super(context);
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {

        return super.getContextMenuInfo();
    }
}
