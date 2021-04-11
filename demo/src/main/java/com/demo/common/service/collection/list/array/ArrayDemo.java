package com.demo.common.service.collection.list.array;

import org.junit.Test;

import java.util.Arrays;

/**
 * list 自定义实现见:
 *
 * @see com.demo.common.service.algorithm.math.list
 */
public class ArrayDemo {
    @Test
    public void copy() {
        String[] from = new String[]{"a", "c", "E", "b"};
        System.out.println(from);
        for (String s : from) {
            System.out.println(s);
        }

        System.out.println("===============================");

        String[] to = Arrays.copyOf(from, 2);
        System.out.println(to);
        for (String s : to) {
            System.out.println(s);
        }

        System.out.println("===============================");

        String[] to2 = new String[from.length];
        System.arraycopy(from, 1, to2, 1, 2);
        System.out.println(to2);
        for (String s : to2) {
            System.out.println(s);
        }
    }

}
