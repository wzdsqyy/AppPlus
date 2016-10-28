package com.wzdsqyy.applibDemo.viewpager.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wzdsqyy.applibDemo.R;

/**
 * Created by Administrator on 2016/10/13.
 */

public class DecorationAdapter extends PagerAdapter{
    private int count;

    public DecorationAdapter() {
        this(5);
    }

    public DecorationAdapter(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(container.getContext());
        imageView.setImageResource(R.mipmap.ic_launcher);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
