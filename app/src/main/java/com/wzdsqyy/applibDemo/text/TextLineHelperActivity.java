package com.wzdsqyy.applibDemo.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.wzdsqyy.applib.utils.helper.TextLineHelper;
import com.wzdsqyy.applibDemo.R;

public class TextLineHelperActivity extends AppCompatActivity implements View.OnClickListener,TextLineHelper.OnLineFinishListener, ViewTreeObserver.OnPreDrawListener {

    private TextView tvContent;
    private TextView tvEx;
    private TextLineHelper textLayoutHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_line_helper);
        findViewById(R.id.tv_ex).setOnClickListener(this);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvEx = (TextView) findViewById(R.id.tv_ex);
        textLayoutHelper = new TextLineHelper.Builder()
                .textView(tvContent)
                .minLines(3)
                .maxLines(50)
                .min2MaxDuration(500)
                .max2MinDuration(500)
                .build().onLineFinishListener(this);
        tvContent.getViewTreeObserver().addOnPreDrawListener(this);
    }
    @Override
    public void onClick(View view) {
        if (R.id.tv_ex == view.getId()) {
            textLayoutHelper.toggleTextLayout();
        }
    }
    /**
     * @param textView
     * @param isMin2Max 是 由最小行数 到 最大行数
     */
    @Override
    public void onLineFinish(TextView textView, boolean isMin2Max) {
        if (isMin2Max) {
            tvEx.setText("收回");
        } else {
            tvEx.setText("展开");
        }
    }

    @Override
    public boolean onPreDraw() {
        int count = tvContent.getLineCount();
        tvEx.setVisibility(count>3?View.VISIBLE:View.GONE);
        return true;
    }
}
