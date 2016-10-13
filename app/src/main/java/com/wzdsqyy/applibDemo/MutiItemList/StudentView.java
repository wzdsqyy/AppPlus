package com.wzdsqyy.applibDemo.MutiItemList;

import android.view.View;
import android.widget.Toast;

import com.wzdsqyy.applib.ui.adapter.MutiHolder;
import com.wzdsqyy.applib.ui.adapter.MutiItemBinder;


/**
 * Created by Administrator on 2016/10/12.
 */

public class StudentView implements MutiItemBinder<StudentModel>, View.OnClickListener {

    private StudentModel bean;

    public static StudentView newInstance() {
        StudentView instance = new StudentView();
        return instance;
    }

    @Override
    public void onClick(View v) {
        if (bean != null) {
            Toast.makeText(v.getContext(), bean.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBindViewHolder(MutiHolder holder, StudentModel bean, int possion) {
        this.bean= (StudentModel) bean;
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void init(MutiHolder holder) {

    }
}
