package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.wzdsqyy.mutiitem.MutiItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.start;

/**
 * Created by Administrator on 2016/11/22.
 */

public class SectionHelper {
    private SparseArray<List<MutiItem>> childs = new SparseArray<>();
    private int headerRes = -1;
    private int footerRes = -1;

    public SectionHelper setHeaderRes(@LayoutRes int headerRes) {
        this.headerRes = headerRes;
        return this;
    }

    public SectionHelper setFooterRes(@LayoutRes int footerRes) {
        this.footerRes = footerRes;
        return this;
    }

    public int findSectionHeaderPossion(int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        for (int i = possion; i > 0; i--) {
            MutiItem node = adapter.getData().get(i);
            if (node.getMutiItem().layoutRes == headerRes) {
                return i;
            }
        }
        return -1;
    }

    public int findSectionFooterPossion(int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        int size = adapter.getData() == null ? 0 : adapter.getData().size();
        for (int i = possion; i < size; i++) {
            MutiItem node = adapter.getData().get(i);
            if (node.getMutiItem().layoutRes == footerRes) {
                return i;
            }
        }
        return -1;
    }

    public List<MutiItem> getContents(int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        int start = findSectionHeaderPossion(possion, adapter);
        if (start == -1) {
            return Collections.EMPTY_LIST;
        }
        int end;
        if (footerRes == -1) {
            end = nextItemPossion(start, adapter.getItem(start), adapter.getData());
        } else {
            end = findSectionFooterPossion(possion, adapter);
        }
        if (end == -1) {
            return adapter.getData().subList(start + 1, adapter.getData().size());
        } else {
            return adapter.getData().subList(start + 1, end);
        }
    }


    public List getListItemss(int start, @NonNull MutiItem self, @NonNull List<MutiItem> items) {
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

    public int nextItemPossion(int start, @NonNull MutiItem self, @NonNull List<MutiItem> items) {
        for (int i = start; i < items.size(); i++) {
            if (self.getMutiItem().isBrotherType(items.get(i))) {
                return i - 1;
            }
        }
        return -1;
    }

    public int preItemPossion(int start, @NonNull MutiItem self, @NonNull List<MutiItem> items) {
        for (int i = start; i >= 0; i--) {
            if (self.getMutiItem().isBrotherType(items.get(i))) {
                return i + 1;
            }
        }
        return -1;
    }

    public int addItem(int index, MutiItem section, @NonNull List<MutiItem> items) {
        return addItems(index, section, null, items);
    }

    public int addItems(int index, @NonNull List<MutiItem> childs, @NonNull List<MutiItem> items) {
        return addItems(index, null, childs, items);
    }

    public int showChilds(int index, @NonNull List<MutiItem> items) {
        return addItems(index, null, childs.get(index, null), items);
    }

    public int deleteChilds(@IntRange(from = 0) int index, @NonNull List<MutiItem> items) {
        if (index > items.size()) {
            return 0;
        }
        List dels = getListItemss(index, items.get(index), items);
        childs.remove(index);
        items.removeAll(dels);
        return dels.size();
    }

    public int hideChilds(@NonNull MutiItem self, @NonNull List<MutiItem> items) {
        int start = items.indexOf(self);
        if (start < 0) {
            return 0;
        }
        List dels = getListItemss(start, self, items);
        childs.put(start, dels);
        items.removeAll(dels);
        return dels.size();
    }

    public int addItems(int index, MutiItem section, List<MutiItem> childs, @NonNull List<MutiItem> items) {
        int count = 0;
        this.childs.put(index, childs);
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
