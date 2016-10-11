package com.wzdsqyy.applibDemo;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/10/11.
 */

public class LastSearch{
    static LinkedList<String> list=new LinkedList<>();

    public static void main(String[] args) {
        boolean contains = list.contains("");
        int index = list.indexOf("");
        if(index!=-1){
            String remove = list.remove(index);
            list.addFirst(remove);
        }
    }
}
