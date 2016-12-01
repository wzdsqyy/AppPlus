package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.wzdsqyy.mutiitem.MutiItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.inAnimation;
import static android.R.attr.start;

/**
 * Created by Administrator on 2016/11/22.
 */

public class SectionHelper {
    private SparseArray<List<MutiItem>> childs = new SparseArray<>();
    private final int startRes;
    private int endRes = -1;

    public SectionHelper(@LayoutRes int section) {
        this.startRes = section;
    }

    public SectionHelper setSectionFooter(@LayoutRes int endRes) {
        this.endRes = endRes;
        return this;
    }

    /**
     * @param possion
     * @param adapter
     * @return -1 没有找到，其他在Adapter中的位置
     */
    public int findSectionPossion(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        for (int i = possion; i > 0; i--) {
            MutiItem node = adapter.getData().get(i);
            if (node.getMutiItem().layoutRes == startRes) {
                return i;
            }
        }
        return -1;
    }

    boolean isSection(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        if (adapter.getItemViewType(possion) == startRes) {
            return true;
        }
        return false;
    }

    public int findSectionFooterPossion(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        if (endRes == -1) {
            throw new RuntimeException("你没有设置SectionFooter");
        }
        int size = adapter.getData() == null ? 0 : adapter.getData().size();
        for (int i = possion; i < size; i++) {
            MutiItem node = adapter.getData().get(i);
            if (node.getMutiItem().layoutRes == endRes && i != possion) {
                return i;
            }
        }
        return -1;
    }

    public int nextItemPossion(@IntRange(from = 0) int start, @NonNull MutiItem self, @NonNull List<MutiItem> items) {
        for (int i = start; i < items.size(); i++) {
            if (self.getMutiItem().isBrotherType(items.get(i))) {
                return i - 1;
            }
        }
        return -1;
    }

    public List<MutiItem> getContents(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        int start = possion;
        if (!isSection(possion, adapter)) {
            start = findSectionPossion(possion, adapter);
        }
        int end;
        if (endRes != -1) {
            end = findSectionFooterPossion(possion, adapter);
        } else {
            end = nextItemPossion(start, adapter.getItem(start), adapter.getData());
        }
        if (end == -1) {
            return adapter.getData().subList(start + 1, adapter.getData().size());
        } else {
            return adapter.getData().subList(start + 1, end);
        }
    }

    public List<Integer> getContentPossions(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        int start = findSectionPossion(possion, adapter);
        if (start == -1) {
            return Collections.EMPTY_LIST;
        }
        int end;
        if (endRes != -1) {
            end = findSectionFooterPossion(possion, adapter);
        } else {
            end = nextItemPossion(start, adapter.getItem(start), adapter.getData());
        }
        if (end == -1) {
            end = adapter.getItemCount() - 1;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = start + 1; i < end; i++) {
            list.add(i);
        }
        return list;
    }

    public int showContents(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        int section = findSectionPossion(possion, adapter);
        if (hasContents(section, adapter)) {
            List<MutiItem> items = childs.get(section, Collections.EMPTY_LIST);
            if (items.isEmpty()) {
                return 0;
            }
            adapter.getData().addAll(items);
            childs.remove(possion);
            return items.size();
        }
        return 0;
    }

    public boolean isShowContents(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        if (!isSection(possion, adapter)) {
            return true;
        }
        if (possion + 1 >= adapter.getItemCount()) {
            return false;
        }
        if (isSection(possion + 1, adapter)) {
            return true;
        }
        return false;
    }

    public boolean hasContents(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        if (!isSection(possion, adapter)) {
            return true;
        }
        List<MutiItem> items = childs.get(possion, null);
        if (items != null) {
            return true;
        }
        if (possion + 1 >= adapter.getItemCount()) {
            return false;
        }
        if (endRes == -1) {
            return isSection(possion + 1, adapter);
        } else {
            return isFooter(possion + 1, adapter);
        }
    }

    private boolean isFooter(@IntRange(from = 0) int possion, MutiItemAdapter<MutiItem> adapter) {
        if (adapter.getItemViewType(possion) == endRes) {
            return true;
        }
        return false;
    }

    public int hideContents(@IntRange(from = 0) int possion, @NonNull MutiItemAdapter<MutiItem> adapter) {
        int section = possion;
        if (!isSection(possion, adapter)) {
            section = findSectionPossion(possion, adapter);
        }
        List<MutiItem> items = getContents(section, adapter);
        adapter.getData().removeAll(items);
        childs.put(possion, items);
        return items.size();
    }

    public static int addItems(MutiItem section, List<MutiItem> childs, @NonNull List<MutiItem> datas) {
        int count = 0;
        if (section != null) {
            count++;
            datas.add(section);
        }
        if (childs == null) {
            return count;
        }
        datas.addAll(childs);
        return count + childs.size();
    }

    public static int addItemsAtIndex(@IntRange(from = 0) int index, MutiItem section, List<MutiItem> childs, @NonNull List<MutiItem> items) {
        int count = 0;
        if (section != null) {
            items.add(index, section);
            count++;
            index++;
        }
        count = count + (childs == null ? 0 : childs.size());
        items.addAll(index, childs);
        return count;
    }
}
