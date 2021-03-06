package com.wzdsqyy.applibDemo.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.utils.helper.TextLineHelper;

import java.util.ArrayList;
import java.util.Collection;

public class TextLineHelperActivity extends AppCompatActivity implements View.OnClickListener,TextLineHelper.OnLineFinishListener, ViewTreeObserver.OnPreDrawListener {

    private TextView tvContent;
    private TextView tvEx;
    private TextLineHelper textLayoutHelper;
    private ListView listView;
    private ArrayAdapter adapter;
    private Collection testArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_line_helper);
        listView = (ListView) findViewById(R.id.list_itemitem);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.addAll(getTestArray());
        adapter.notifyDataSetChanged();
//        findViewById(R.id.tv_ex).setOnClickListener(this);
//        tvContent = (TextView) findViewById(R.id.tv_content);
//        tvEx = (TextView) findViewById(R.id.tv_ex);
//        textLayoutHelper = new TextLineHelper.Builder()
//                .textView(tvContent)
//                .minLines(3)
//                .maxLines(50)
//                .min2MaxDuration(500)
//                .max2MinDuration(500)
//                .build().onLineFinishListener(this);
//        tvContent.getViewTreeObserver().addOnPreDrawListener(this);
    }
    @Override
    public void onClick(View view) {
//        if (R.id.tv_ex == view.getId()) {
//            textLayoutHelper.toggleTextLayout();
//        }
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

    public ArrayList getTestArray() {
        ArrayList list=new ArrayList();
        for (int i = 0; i < 50; i++) {
            list.add("index"+i);
        }
        return list;
    }
}
