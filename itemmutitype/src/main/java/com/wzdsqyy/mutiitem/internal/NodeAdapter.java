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
        return node.getNodeHelper().setExpand(expand, getData());
    }

    public int toggleExpand(int position){
      return toggleExpand(getData().get(position));
    }

    public int toggleExpand(Node model){
       return model.getNodeHelper().toggleExpand(getData());
    }

    @Override
    public NodeList getData() {
        return super.getData();
    }
}
