package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * 分组列表,注意需要将二级列表映射为一级列表，同一组的数据需保证连续，不连续的视为不同组
 */
public class SectionAdapter extends MutiItemAdapter {

    private SectionHelper helper;

    public SectionAdapter(MutiItemBinderFactory factory) {
        super(factory);
    }

    public MutiItemAdapter setSessionType(@LayoutRes int layoutRes) {
        boolean register = isRegister(layoutRes);
        if (register) {
            if (helper == null) {
                helper = new SectionHelper(layoutRes, this);
            } else {
                helper.setSectionType(layoutRes);
            }
        } else {
            throw new RuntimeException("must register layoutRes frist");
        }
        return this;
    }

    public int getSection(int possion) {
        return helper.getSection(possion);
    }

    /**
     * 添加数据到某组
     * @param data
     * @param sectionPosition
     */
    public void addSectionData(List<MutiItemSuport> data,int sectionPosition) {
        addData(data,sectionPosition);
    }

    /**
     * 获取指定所属组在列表中的位置
     * @param position
     */
    public void getSectionPosition(int position) {
        helper.getSectionPossion(position);
    }

    public boolean isExpand(int position) {
        return helper.isExpand(position);
    }

    public void setSection(int position, boolean expand) {
        helper.setSection(position, expand);
    }

    public int getSectionCount() {
        return helper.getSectionCount();
    }

    public void deleteSection(int possion) {
        helper.deleteSection(possion);
    }

    public void deleteSectionItems(int possion) {
        helper.deleteSectionItem(possion);
    }
}
