package com.demo.common.service.collection.list;

import com.demo.common.service.collection.list.abs.ListInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

/**
 * list 自定义实现见:
 *
 * @see com.demo.common.service.algorithm.math.list
 */
public class ListDemo extends ListInfo {
    @Test
    public void isEmpty() {
        System.out.println(listA.isEmpty());
        System.out.println(listB.isEmpty());
        System.out.println(listC.isEmpty());
        System.out.println(listD.isEmpty());
        System.out.println(listE);
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

    /**
     * 数组复制
     */
    @Test
    public void copyOf() {
        System.out.println(arrayE.length);
        arrayE = Arrays.copyOf(arrayE, 9);
        System.out.println(arrayE.length);
    }

    /**
     * 差集
     */
    @Test
    public void removeAll() {
        listA.removeAll(listB);
        System.out.println(listA);
        System.out.println(listB);
    }

    /**
     * forEach
     */
    @Test
    public void forEach() {
        listA.forEach(s -> {
            System.out.println(s);
        });
        System.out.println("===============");

        for (String s : listA) {
            System.out.println(s);
        }
    }

    /**
     * iterator
     */
    @Test
    public void iterator() {
        Iterator<String> iterator = listA.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }


    /**
     * listIterator
     */
    @Test
    public void listIterator() {
        Iterator<String> iterator = listA.listIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
