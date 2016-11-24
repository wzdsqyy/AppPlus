package com.wzdsqyy.applibDemo.MutiItemList.binder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wzdsqyy.applibDemo.MutiItemList.bean.StudentModel;
import com.wzdsqyy.mutiitem.MutiItemBinder;


/**
 * Created by Administrator on 2016/10/12.
 */

public class StudentView implements MutiItemBinder<StudentModel>, View.OnClickListener {

    private StudentModel bean;
    RecyclerView.ViewHolder holder;
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
    public void onBindViewHolder(StudentModel bean, int possion) {
        this.bean= bean;
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void init(RecyclerView.ViewHolder holder,RecyclerView.Adapter adapter) {
        this.holder=holder;
    }
}
