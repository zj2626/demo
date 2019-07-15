package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.interfaces.ListInterface;
import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.array.MyArrayList;
import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.link.MSingleyLinkedList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListDemo {
    @Test
    public void testArrayList() {
        List<Object> arrayList = new ArrayList<>();
        arrayList.add(12);
        arrayList.add("aa");
        arrayList.add(11.22);
        System.out.println(arrayList.isEmpty());
        System.out.println(arrayList.size());
        System.out.println(arrayList.get(1));
        System.out.println(arrayList);
    }

    @Test
    public void testMyArrayList() {
        ListInterface<Object> arrayList = new MyArrayList<>();
        arrayList.add(12);
        arrayList.add("aa");
        arrayList.add(11.22);
        arrayList.add(1, 776);
        arrayList.add(0, 777);
        System.out.println(arrayList.isEmpty());
        System.out.println(arrayList.size());
        System.out.println(arrayList.get(1));
        System.out.println(arrayList);
        arrayList.add(12);
        arrayList.add("aa");
        arrayList.add(11.22);
        arrayList.set(1, 999);
        System.out.println(arrayList);
    }

    /************************************/

    @Test
    public void testLinkedList() {
        List<Object> arrayList = new LinkedList<>();
        arrayList.add(12);
        arrayList.add("aa");
        arrayList.add(11.22);
        System.out.println(arrayList.isEmpty());
        System.out.println(arrayList.size());
        System.out.println(arrayList.get(1));
        System.out.println(arrayList);
    }

    @Test
    public void testMyLinkedList() {
        ListInterface<Object> arrayList = new MSingleyLinkedList<>();
        arrayList.add(12);
        arrayList.add("aa");
        arrayList.add(11.22);
        arrayList.add(1, 776);
        arrayList.add(0, 777);
        System.out.println(arrayList.isEmpty());
        System.out.println(arrayList.size());
        System.out.println(arrayList.get(1));
        System.out.println(arrayList);
        arrayList.add(12);
        arrayList.add("aa");
        arrayList.add(11.22);
        arrayList.set(1, 999);
        System.out.println(arrayList);
        System.out.println(arrayList.remove(0));
        System.out.println(arrayList);
        System.out.println(arrayList.remove(6));
        System.out.println(arrayList);
    }
}
