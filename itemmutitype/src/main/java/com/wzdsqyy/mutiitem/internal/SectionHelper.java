package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;

import com.wzdsqyy.mutiitem.MutiItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */

public class SectionHelper {
    public static List getListItemss(int start, @NonNull MutiItem self, @NonNull List<MutiItem> items) {
        ArrayList list = new ArrayList<>();
        for (int i = start; i < items.size(); i++) {
            MutiItem node = items.get(i);
            if (self.getMutiItem().isBrotherType(node)) {
                return list;
            }
            list.add(node);
        }
        return list;
    }

    public static int delItemss(@NonNull MutiItem self, @NonNull List<MutiItem> items) {
        int start = items.indexOf(self);
        if (start < 0) {
            return 0;
        }
        List dels = getListItemss(start, self, items);
        items.removeAll(dels);
        return dels.size();
    }


    public static int nextItem(int start, @NonNull MutiItem self, @NonNull List<MutiItem> items) {
        List itemss = getListItemss(start, self, items);
        items.removeAll(itemss);
        return itemss.size();
    }

    public static int preItem(int start, @NonNull MutiItem self, @NonNull List<MutiItem> items) {
        for (int i = start; i > 0; i--) {
            if (self.getMutiItem().isBrotherType(items.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static int addItemss(int index, MutiItem section, @NonNull List<MutiItem> items) {
        return addItemss(index, section, null, items);
    }

    /**
     * 指定位置之后显示
     *
     * @param section
     * @param childs
     * @param items
     * @return
     */
    public static int showItemss(MutiItem section, @NonNull List<MutiItem> childs, @NonNull List<MutiItem> items) {
        return addItemss(items.indexOf(section) + 1, null, childs, items);
    }

    public static int addItemss(int index, MutiItem section, List<MutiItem> childs, @NonNull List<MutiItem> items) {
        int count = 0;
        if (section != null) {
            items.add(index, section);
            count++;
            index++;
        }
        if (childs == null) {
            return count;
        }
        items.addAll(index, childs);
        count = count + items.size();
        return count;
    }
}
