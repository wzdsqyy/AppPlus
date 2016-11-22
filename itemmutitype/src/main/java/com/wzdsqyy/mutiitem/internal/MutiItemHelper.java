package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.wzdsqyy.mutiitem.MutiItem;
import com.wzdsqyy.mutiitem.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public class MutiItemHelper implements MutiItem {
    private int layoutRes = -1;

    int getMutiItemViewType() {
        return layoutRes;
    }

    void setMutiItemViewType(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }

    @Override
    public MutiItemHelper getMutiItem() {
        return this;
    }

    boolean isSameType(MutiItem other) {
        if (other.getMutiItem().getMutiItemViewType() == layoutRes) {
            return true;
        }
        return false;
    }

    /**
     * 在指定索引之后没有此类ViewType了
     *
     * @param start
     * @param list
     * @return
     */
    public static int getNextSameType(int start, List<MutiItem> list) {
        MutiItem me = list.get(start), node;
        for (int i = start + 1; i < list.size(); i++) {
            node = list.get(i);
            if (me.getMutiItem().isSameType(node)) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<MutiItem> getMutiItems(int start, List<MutiItem> rootList) {
        ArrayList<MutiItem> items = new ArrayList<>();
        MutiItem item = rootList.get(start);
        for (int i = start; i < rootList.size(); i++) {
            MutiItem node = rootList.get(i);
            if (!item.getMutiItem().isSameType(node)) {
                items.add(node);
                continue;
            }
            if (item.getMutiItem().isSameType(node)) {
                return items;
            }
        }
        return items;
    }

    public static ArrayList<MutiItem> getMutiItems(MutiItem item, List<MutiItem> rootList) {
        int start=rootList.indexOf(item);
        ArrayList<MutiItem> items = new ArrayList<>();
        if(start==-1){
            return items;
        }
        start++;
        for (int i = start; i < rootList.size(); i++) {
            MutiItem node = rootList.get(i);
            if (!item.getMutiItem().isSameType(node)) {
                items.add(node);
                continue;
            }
            if (item.getMutiItem().isSameType(node)) {
                return items;
            }
        }
        return items;
    }

    /**
     * 在指定索引之前没有此类ViewType了
     *
     * @param start
     * @param items
     * @return
     */
    public static int getPreSameType(int start, List<MutiItem> items) {
        MutiItem me = items.get(start), node;
        for (int i = start - 1; i > 0; i--) {
            node = items.get(i);
            if (me.getMutiItem().isSameType(node)) {
                return i;
            }
        }
        return -1;
    }
}
