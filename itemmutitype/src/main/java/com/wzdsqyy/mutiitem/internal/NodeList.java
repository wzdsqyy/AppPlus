package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;

import com.wzdsqyy.mutiitem.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2016/11/18.
 */

public class NodeList implements List<Node> {
    private List<Node> mNodes;
    private SparseBooleanArray mExpands = new SparseBooleanArray();
    private boolean mDefaultExpands = true;

    public static NodeList getNodeList(boolean defaultExpands, @Nullable List<Node> list) {
        NodeList nodes = new NodeList(list);
        nodes.setDefaultExpands(defaultExpands);
        return nodes;
    }

    NodeList setDefaultExpands(boolean mDefaultExpands) {
        if (mDefaultExpands != this.mDefaultExpands) {
            this.mDefaultExpands = mDefaultExpands;
            if (!isEmpty()) {
                init(mNodes);
            }
        }
        return this;
    }

    public NodeList setExpands(int nodeType, boolean expand) {
        mExpands.put(nodeType, expand);
        if (!isEmpty()) {
            for (int i = 0; i < size(); i++) {
                get(i).getNodeHelper().setExpand(expand, mNodes);
            }
        }
        return this;
    }

    private NodeList(@Nullable List<Node> root) {
        this.mNodes = root;
        if (root == null) {
            this.mNodes = new ArrayList<>();
        }
        init(root);
    }

    private void init(List<Node> nodes) {
        if (nodes.isEmpty()) {
            return;
        }
        ArrayList<Node> list = new ArrayList();
        Collections.copy(list, nodes);
        nodes.clear();
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public int size() {
        return mNodes.size();
    }

    @Override
    public boolean isEmpty() {
        return mNodes.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mNodes.contains(o);
    }

    @NonNull
    @Override
    public Iterator<Node> iterator() {
        return mNodes.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return mNodes.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] a) {
        return mNodes.toArray(a);
    }

    @Override
    public boolean add(Node node) {
        boolean expands = mExpands.get(node.getNodeType(), mDefaultExpands);
        node.getNodeHelper().setExpand(expands);
        return node.getNodeHelper().addSelf(mNodes);
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Node) {
            ((Node) o).getNodeHelper().removeSelf(mNodes);
            return true;
        }
        return mNodes.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return mNodes.contains(c);
    }

    @Override
    public boolean addAll(Collection<? extends Node> c) {
        Iterator<? extends Node> iterator = c.iterator();
        boolean result = false;
        while (iterator.hasNext()) {
            add(iterator.next());
        }
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Node> c) {
        Iterator<? extends Node> iterator = c.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator iterator = c.iterator();
        boolean result = false;
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Node) {
                remove(o);
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return mNodes.retainAll(c);
    }

    @Override
    public void clear() {
        mNodes.clear();
    }

    @Override
    public Node get(int index) {
        return mNodes.get(index);
    }

    @Override
    public Node set(int index, Node node) {
        boolean expands = mExpands.get(node.getNodeType(), mDefaultExpands);
        node.getNodeHelper().setExpand(expands);
        return node.getNodeHelper().setSelf(index, mNodes);
    }

    @Override
    public void add(int index, Node node) {
        boolean expands = mExpands.get(node.getNodeType(), mDefaultExpands);
        node.getNodeHelper().setExpand(expands);
        node.getNodeHelper().addSelf(index, mNodes);
    }

    @Override
    public Node remove(int index) {
        return get(index).getNodeHelper().removeSelf(mNodes);
    }

    @Override
    public int indexOf(Object o) {
        return mNodes.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return mNodes.lastIndexOf(o);
    }

    @Override
    public ListIterator<Node> listIterator() {
        return mNodes.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<Node> listIterator(int index) {
        return mNodes.listIterator(index);
    }

    @NonNull
    @Override
    public List<Node> subList(int fromIndex, int toIndex) {
        return mNodes.subList(fromIndex, toIndex);
    }
}
