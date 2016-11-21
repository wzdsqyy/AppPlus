package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wzdsqyy.mutiitem.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * 利用递归与自定义List 实现层级的List
 */

public class NodeHelper implements Node {
    private final int mNodeLevel;
    private boolean mExpand;
    private Node parent = null;
    private List<Node> mChilds = null;
    private MutiItemHelper helper = new MutiItemHelper();
    private int mChildType = -1;

    public NodeHelper(int mNodeType) {
        this(mNodeType, true);
    }

    public NodeHelper(int mNodeType, boolean expand) {
        this.mNodeLevel = mNodeType;
        this.mExpand = expand;
    }

    /**
     * 设置子节点,若设置为Null表示没有子节点了，默认没有子节点
     *
     * @param childs
     */
    public void setChilds(@Nullable List<Node> childs) {
        this.mChilds = childs;
        if (childs == null) {
            return;
        }
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).getNodeHelper().parent = this;
        }
    }

    public boolean isExpand() {
        return mExpand;
    }

    /**
     * 数据没有变化是返回false
     *
     * @param expand
     * @return
     */
    boolean setExpand(boolean expand) {
        mExpand = expand;
        return true;
    }

    /**
     * 改变展开状态
     *
     * @return
     */
    int toggleExpand(@NonNull List<Node> root) {
        return setExpand(!mExpand, root);
    }

    /**
     * 数据没有变化是返回false
     *
     * @param expand
     * @return
     */
    int setExpand(boolean expand, @NonNull List<Node> root) {
        if (mExpand == expand || mChilds == null) {
            return 0;
        }
        mExpand = expand;
        int index = root.indexOf(this) + 1;
        if (expand) {
            addChilds(index, root);
        } else {
            removeChilds(root);
        }
        return mChilds.size();
    }

    private int addChilds(int index, @NonNull List<Node> root) {
        if (!isExpand() || mChilds == null || index == -1) {
            return 0;
        }
        int count = mChilds.size();
        for (int i = 0; i < count; i++) {
            index = mChilds.get(i).getNodeHelper().addSelf(index, root);
        }
        return index;
    }

    Node removeSelf(@NonNull List<Node> root) {
        boolean result = root.remove(this);
        if (result) {
            removeChilds(root);
            return this;
        }
        return null;
    }

    private boolean removeChilds(@NonNull List<Node> root) {
        if (mChilds == null || root.size() == 0) {
            return false;
        }
        int start = root.indexOf(this) + 1;
        mChilds.clear();
        boolean result = false;
        findChilds(root, start);
        for (int i = 0; i < mChilds.size(); i++) {
            Node node = mChilds.get(i);
            result = node.getNodeHelper().removeSelf(root) != null;
        }
        return result;
    }

    /**
     * @param root
     * @param start
     * @return
     */
    private void findChilds(@NonNull List<Node> root, int start) {//找出要结束的位置
        for (int i = start; i < root.size(); i++) {//记录要删除的项
            Node node = root.get(i);
            if (isMyChild(node)) {
                mChilds.add(node);
            } else if (isBrother(node)) {
                return;
            }
        }
    }

    private boolean isMyChild(Node node) {
        return node.getNodeHelper().parent == this;
    }

    private boolean isBrother(Node node) {
        return node.getNodeHelper().parent == parent;
    }

    boolean addSelf(@NonNull List<Node> root) {
        boolean result = root.add(this);
        if (result) {
            result = addChilds(root);
        }
        return result;
    }

    private boolean addChilds(@NonNull List<Node> root) {
        boolean result = false;
        if (isExpand() && mChilds != null) {
            for (int i = 0; i < mChilds.size(); i++) {
                result = mChilds.get(i).getNodeHelper().addSelf(root);
            }
        }
        return result;
    }

    int addSelf(int index, @NonNull List<Node> root) {
        if (index < 0 || index > root.size()) {
            return -1;
        }
        root.add(index, this);
        index = index + 1;
        if (isExpand() && mChilds != null) {
            for (int i = 0; i < mChilds.size(); i++) {
                index = mChilds.get(i).getNodeHelper().addSelf(index, root);
            }
        }
        return index;
    }

    Node setSelf(int index, @NonNull List<Node> root) {
        Node old = root.set(index, this);
        old.getNodeHelper().removeChilds(root);
        if (isExpand() && mChilds != null) {
            for (int i = 0; i < mChilds.size(); i++) {
                mChilds.get(i).getNodeHelper().addSelf(index, root);
            }
        }
        return old;
    }

    public static List<Node> getChilds(Node model, List<Node> root) {
        int start = root.indexOf(model);
        ArrayList<Node> list = new ArrayList<>();
        if (start == -1) {
            return list;
        }
        for (int i = 0; i < root.size(); i++) {
            Node node = root.get(i);
            if (model.getNodeHelper().isMyChild(node)) {
                list.add(node);
                continue;
            }
            if (model.getNodeHelper().isBrother(node)) {
                return list;
            }
        }
        return list;
    }

    public static int getNextBrotherIndex(Node model, List<Node> root) {
        int start = root.indexOf(model);
        if (start == -1) {
            return -1;
        }
        Node node;
        for (int i = start; i < root.size(); i++) {
            node = root.get(i);
            if (model.getNodeHelper().isBrother(node)) {
                return i;
            }
        }
        return -1;
    }

    public static int getNextBrotherIndex(int start, List<Node> root) {
        Node me = root.get(start), node;
        for (int i = start+1; i < root.size(); i++) {
            node = root.get(i);
            if (me.getNodeHelper().isBrother(node)) {
                return i;
            }
        }
        return -1;
    }


    public int getNodeType() {
        return mNodeLevel;
    }

    public boolean havaChilds() {
        return mChilds != null;
    }

    @Override
    public NodeHelper getNodeHelper() {
        return this;
    }

    @Override
    public MutiItemHelper getMutiItem() {
        return helper;
    }
}
