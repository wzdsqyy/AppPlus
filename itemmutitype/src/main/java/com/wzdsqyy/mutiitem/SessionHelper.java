package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SessionHelper extends RecyclerView.AdapterDataObserver {
    private MutiItemAdapter<SessionSupport> adapter;
    private int mSectionType;
    private SparseIntArray cache;
    private ArrayList<Integer> sessions;
    private int mSectionCount = 0;

    public SessionHelper(@LayoutRes int viewType, @NonNull MutiItemAdapter<SessionSupport> adapter) {
        this.mSectionType = viewType;
        this.adapter = adapter;
        cache = new SparseIntArray();
        sessions = new ArrayList<>();
        adapter.registerAdapterDataObserver(this);
        calculate(0);
    }

    public SessionHelper setSectionType(int mSectionType) {
        this.mSectionType = mSectionType;
        return this;
    }

    private void calculate(int start) {
        cache.clear();
    }

    @Override
    public void onChanged() {
        super.onChanged();
        calculate(0);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        calculate(positionStart);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        calculate(positionStart);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        calculate(positionStart);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        if (fromPosition < toPosition) {
            calculate(fromPosition);
        } else {
            calculate(toPosition);
        }
    }

    /**
     * 统计数目
     *
     * @return
     */
    public int getSectionCount() {
        return mSectionCount;
    }

    public boolean deleteSection(int possion) {
        SessionSupport item = adapter.getItem(possion);
        return deleteSubItems(possion+1,item);
    }

    public boolean deleteSectionItems(int possion) {
        SessionSupport item = adapter.getItem(possion);
        boolean all = deleteSubItems(possion, item);
        return all;
    }

    private boolean deleteSubItems(int possion, SessionSupport item) {
        List<SessionSupport> list = adapter.getData().subList(possion, item.getSessionCount());
        boolean all = adapter.getData().removeAll(list);
        if (all) {
            adapter.notifyItemRangeRemoved(possion, item.getSessionCount());
        }
        return all;
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

    private static class SessionItem {
        final int session;//第几组
        int possion = -1;//索引位置

        public SessionItem(int session) {
            this.session = session;
        }
    }
}
