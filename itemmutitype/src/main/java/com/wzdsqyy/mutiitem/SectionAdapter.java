package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;

/**
 * 分组列表
 */
public class SectionAdapter extends MutiItemAdapter<SectionSupport> {

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


    public boolean isExpand(int position){
        return helper.isExpand(position);
    }

    public void setSection(int position, boolean expand) {
        helper.setSection(position,expand);
    }

    public int getSectionCount(){
        return helper.getSectionCount();
    }

    public void deleteSection(int possion){
        helper.deleteSection(possion);
    }

    public void deleteSectionItems(int possion){
        helper.deleteSectionItem(possion);
    }
}
