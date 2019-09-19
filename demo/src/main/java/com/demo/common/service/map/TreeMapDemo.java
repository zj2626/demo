package com.demo.common.service.map;

import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("aa", "abc");
        map.put("_a", "abc");
        map.put("b", "abb");
        map.put("c", "aab");
        map.put("ca", "aab");
        map.put("c1", "aab");
        map.put("ac", "aab");
        map.put("d", "aaa");
        map.put("e", "acc");
        map.put("ab", "acc");

        System.out.println(map);
        System.out.println(map.firstKey());
        System.out.println();

        SortedMap<String, String> result0 = map.tailMap("c");
        SortedMap<String, String> result1 = map.headMap("c");
        System.out.println(result0);
        System.out.println(result1);
        System.out.println(result0.firstKey());
        System.out.println(result1.firstKey());
    }
}
