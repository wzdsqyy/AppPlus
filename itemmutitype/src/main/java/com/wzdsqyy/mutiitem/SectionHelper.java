package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 适合小数据量的分组处理
 */

class SectionHelper extends RecyclerView.AdapterDataObserver {
    private MutiItemAdapter adapter;
    private int mSectionType;
    private SparseIntArray cache;
    private ArrayList<Integer> sessions;
    private int mSectionCount = -1;
    private SparseIntArray section;

    public SectionHelper(@LayoutRes int viewType, @NonNull MutiItemAdapter adapter) {
        this.mSectionType = viewType;
        this.adapter = adapter;
        cache = new SparseIntArray();
        sessions = new ArrayList<>();
        adapter.registerAdapterDataObserver(this);
        resetCache();
    }

    public SparseIntArray getSection() {
        if (section == null) {
            section = new SparseIntArray();
        }
        return section;
    }

    public SectionHelper setSectionType(int mSectionType) {
        this.mSectionType = mSectionType;
        return this;
    }

    public boolean isSection(int position) {
        return adapter.getItemViewType(position) == mSectionType;
    }

    private void resetCache() {
        cache.clear();
        mSectionCount = -1;
        if (section != null) {
            section.clear();
        }
    }

    @Override
    public void onChanged() {
        super.onChanged();
        resetCache();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        resetCache();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        resetCache();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        resetCache();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        if (fromPosition < toPosition) {
            resetCache();
        } else {
            resetCache();
        }
    }

    /**
     * 统计数目
     *
     * @return
     */
    public int getSectionCount() {
        if (mSectionCount == -1) {
            int start = 0;
            int count = 0;
            while (start < adapter.getItemCount()) {
                if (adapter.getItemViewType(start) == mSectionType) {
                    count++;
                    break;
                }
                start++;
            }
            mSectionCount = count;
        }
        return mSectionCount;
    }

    /**
     * 获取指定位置的possion是第几组
     *
     * @param possion
     * @return -1表示没有获取到分组信息
     */
    // TODO: 2016/11/17 需要优化缓存设计，当没有命中缓存时需要遍历整个数据集,目前耗时O(n)
    int getSection(int possion) {
        int section = getSection().get(possion, -1);
        if (section == -1) {
            int start = possion;
            int count = -1;
            while (start >= 0) {
                if (adapter.getItemViewType(start) == mSectionType) {
                    count++;
                }
                start--;
            }
            section = count;
            getSection().put(possion, count);
        }
        return section;
    }

    /**
     * @param possion
     * @return null 如果没有删除任何数据
     */
    public List deleteSection(int possion) {
        return deleteSubItems(possion, true);
    }

    /**
     * @param possion
     * @param expand  true 展开
     * @return false 失败
     */
    public boolean setSection(int possion, boolean expand) {
        Object o = adapter.getItem(possion);
        if (!(o instanceof SectionSupport)) {
            throw new RuntimeException("绑定的数据未实现ExpandItemSupport 接口");
        }
        if (adapter.getItemViewType(possion) != mSectionType) {
            possion = getSectionPossion(possion);
        }
        SectionSupport item = (SectionSupport) o;
        if (expand && !haveSectionItem(possion)) {
            adapter.addData(item.getSectionItems(), possion);
        } else if (!expand && haveSectionItem(possion)) {
            item.setSectionItems(deleteSubItems(possion, false));
        }
        return false;
    }

    /**
     * @param possion
     * @return 是否展开
     */
    public boolean isExpand(int possion) {
        return haveSectionItem(possion);
    }


    /**
     * @param possion
     * @return null 如果没有删除任何数据
     */
    public boolean haveSectionItem(int possion) {
        if (adapter.getItemViewType(possion) == mSectionType
                && possion + 1 < adapter.getItemCount()
                && adapter.getItemViewType(possion + 1) == mSectionType) {
            return false;
        }
        return true;
    }

    /**
     * @param possion
     * @return null 如果没有删除任何数据
     */
    public List deleteSectionItem(int possion) {
        return deleteSubItems(possion, false);
    }

    /**
     * @param possion
     * @return null 如果没有删除任何数据
     */
    private List deleteSubItems(int possion, boolean containsSection) {
        boolean isSession = false;
        if (adapter.getItemViewType(possion) == mSectionType) {
            isSession = true;//选中的刚好是组
        }
        int start = possion;
        if (!isSession) {
            start = getSectionPossion(possion);
        }
        if (start == -1) {
            return null;//这个位置不属于任何组，直接返回flase
        }
        if (isSession) {
            possion = possion + 1;
        }
        int end = getSectionPossionNext(possion);
        if (end == -1) {
            end = adapter.getItemCount();//到结束未找到下一组位置，说明到结束属于start 这个组
        }
        if (!containsSection) {
            start++;
        }
        if (end <= start) {
            return null;
        }
        ArrayList list = new ArrayList(end - start);
        for (int i = start; i < end; i++) {
            Object remove = adapter.getData().remove(start);
            list.add(remove);
        }
        adapter.notifyItemRangeRemoved(start, end - start);
        return list;
    }

    /**
     * 获取前一个Section的Adapter possion
     *
     * @param possion
     * @return
     */
    public int getSectionPossion(int possion) {
        int session = cache.get(possion, -1);
        if (session != -1) {
            return session;
        }
        int start = possion;
        while (start > 0) {
            if (adapter.getItemViewType(start) == mSectionType) {
                session = start;
                break;
            }
            start--;
        }
        cache.put(possion, session);
        return session;
    }

    /**
     * 获取后一个Section的Adapter possion
     *
     * @param possion
     * @return
     */
    public int getSectionPossionNext(int possion) {
        int session = cache.get(possion, -1);
        if (session != -1) {
            return session;
        }
        int start = possion;
        while (start < adapter.getItemCount()) {
            if (adapter.getItemViewType(start) == mSectionType) {
                session = start;
                break;
            }
            start++;
        }
        cache.put(possion, session);
        return session;
    }
}
