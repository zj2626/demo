package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.interfaces.ListInterface;
import org.junit.Test;

import java.util.ArrayList;
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
        System.out.println(arrayList.isEmpty());
        System.out.println(arrayList.size());
        System.out.println(arrayList.get(1));
        System.out.println(arrayList);
        arrayList.add(12);
        arrayList.add("aa");
        arrayList.add(11.22);
        System.out.println(arrayList);
    }
}
