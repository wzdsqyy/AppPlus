package com.wzdsqyy.mutiitem.internal;

import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.mutiitem.Node;

/**
 * 无限层级列表
 */
public class NodeAdapter extends AbsMutiItemAdapter<Node, NodeList> {
    public NodeAdapter(MutiItemBinderFactory factory) {
        super(factory);
    }

    public int setExpand(Node node, boolean expand) {
        return getData().setExpand(node, expand);
    }

    public int toggleExpand(int position) {
        return getData().toggleExpand(position);
    }

    public int toggleExpand(Node model) {
        return getData().toggleExpand(model);
    }

    @Override
    public NodeList getData() {
        return super.getData();
    }
}
