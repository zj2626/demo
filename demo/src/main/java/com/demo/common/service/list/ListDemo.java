package com.demo.common.service.list;

import com.demo.common.service.list.abs.ListInfo;
import org.junit.Test;

public class ListDemo extends ListInfo {
    @Test
    public void isEmpty() {
        System.out.println(listA.isEmpty());
        System.out.println(listB.isEmpty());
        System.out.println(listC.isEmpty());
        System.out.println(listD.isEmpty());
        System.out.println(listE.isEmpty());
    }

    /**
     * 交集
     */
    @Test
    public void retainAll() {
        boolean b = listA.retainAll(listB);
        System.out.println(b);
        System.out.println(listA);
    }

    /**
     * 判断包含
     */
    @Test
    public void containsAll() {
        boolean b = listA.containsAll(listB);
        System.out.println(b);
    }
}
