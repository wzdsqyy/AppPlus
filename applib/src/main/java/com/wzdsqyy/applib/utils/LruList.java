package com.wzdsqyy.applib.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */

public class LruList<E>{
    public static int DEFAULT_MAX=10;
    private int maxCount;
    private LinkedList<E> linkedList;

    public LruList(int count) {
        linkedList = new LinkedList<>();
        maxCount = count;
    }

    public LruList() {
        this(DEFAULT_MAX);
    }

    public int getMaxCount() {
        return maxCount;
    }

    public LruList setMaxCount(int maxCount) {
        if(maxCount<0||maxCount>Integer.MAX_VALUE){
           maxCount=DEFAULT_MAX;
        }
        if(this.maxCount!=maxCount||maxCount<this.maxCount){
            trimList(maxCount);
        }
        this.maxCount = maxCount;
        return this;
    }

    public LruList add(E obj) {
        int index = linkedList.indexOf(obj);
        if (index != -1) {
            linkedList.remove(index);
        }
        linkedList.addFirst(obj);
        trimList(this.maxCount);
        return this;
    }

    private void trimList(int maxCount) {
        while (linkedList.size() > maxCount) {
            linkedList.removeLast();
        }
    }

    public E getFirst() {
        return linkedList.getFirst();
    }

    public E getLast() {
        return linkedList.getLast();
    }

    public int size() {
        return linkedList.size();
    }

    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    public boolean contains(Object o) {
        return linkedList.contains(o);
    }

    public boolean remove(Object o) {
        return linkedList.remove(o);
    }

    public void clear() {
        linkedList.clear();
    }

    public List<E> getAll() {
        ArrayList list = new ArrayList(linkedList);
        return list;
    }
}
