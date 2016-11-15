package com.wzdsqyy.applibDemo.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.utils.CommonAdapter;
import com.wzdsqyy.commonview.drawable.BadgeDrawable;

/**
 * Created by Administrator on 2016/9/20.
 */

public class AppAdapter extends CommonAdapter<Bean> implements AdapterView.OnItemClickListener {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView item = (TextView) convertView;
        BadgeDrawable drawable;
        if (convertView == null) {
            item = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            drawable=new BadgeDrawable(item.getBackground());
            item.setBackgroundDrawable(drawable);
//            Drawable[] drawables = item.getCompoundDrawables();
//            drawables[0]=drawable;
            drawable.setShowNum(true);
//            item.setCompoundDrawables(drawable,drawables[1],drawables[2],drawables[3]);
        } else {
            drawable = (BadgeDrawable) item.getBackground();
        }
        drawable.setBadgeCount(position);
        drawable.setShowNum(false);
        drawable.setLeft(35);
        switch (position % 7) {
            case 0:
                drawable.setCenterHorizontal(true);
                break;
            case 1:
                drawable.setCenterVertical(true);
                break;
            case 2:
                drawable.setCenterVertical(true);
                drawable.setCenterHorizontal(true);
                break;
            case 4:
                drawable.setLeft(-30);
                break;
            case 5:
                drawable.setTop(-30);
                break;
            default:
                drawable.setShowNum(true);
                break;
        }
        drawable.invalidateSelf();
        item.setText(((Bean) getItem(position)).name);
        return item;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.getContext().startActivity(new Intent(view.getContext(), getBeans().get(position).clazz));
    }
}
