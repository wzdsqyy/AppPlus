package com.wzdsqyy.applibDemo.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.wzdsqyy.applib.ui.drawable.BlurDrawable;
import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.commonview.SimpleDialog;

public class DialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public void dialogButtom(View view) {
        SimpleDialog dialog = SimpleDialog.newInstance(this, SimpleDialog.DEFAULT_BOTTOM);
        dialog.setContentView(R.layout.dialog_buttom);
        dialog.show();
    }

    public void dialogCenter(View view) {
        SimpleDialog dialog = SimpleDialog.newInstance(this, SimpleDialog.DEFAULT_CENTER);
        dialog.setContentView(R.layout.dialog_buttom);
        dialog.show();
    }

    public void dialogTop(View view) {
        SimpleDialog dialog = new SimpleDialog(this);
        dialog.setGravity(Gravity.TOP);
        dialog.setContentView(R.layout.dialog_buttom);
        dialog.show();
    }

    public void dialogLoading(View view) {
        SimpleDialog dialog = SimpleDialog.newInstance(this, SimpleDialog.DEFAULT_LOADING);
        dialog.setContentView(R.layout.dialog_buttom);
        dialog.show();
    }

    public void dialogBlur(View view) {
        BlurDrawable drawable = new BlurDrawable(this);
        SimpleDialog dialog = new SimpleDialog(this);
        dialog.setContentView(R.layout.dialog_buttom);
        dialog.setBackgroundDrawable(drawable);
        dialog.setGravity(Gravity.CENTER)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}
