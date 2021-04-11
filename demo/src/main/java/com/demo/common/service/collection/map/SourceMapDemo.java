package com.demo.common.service.collection.map;

import com.demo.common.service.collection.map.source.HashMapDemo;
import org.junit.Test;

public class SourceMapDemo {
    @Test
    public void addTest() {
        HashMapDemo<String, String> map = new HashMapDemo<>(3);
        System.out.println(map);
        map.put("a", "a");
        map.put("b", "b");
        map.put("c", "c");
        System.out.println(map);
        map.put("d", "d");

//        for (int i = 0; i < 30; i++) {
//            map.put("a" + i, "aaa" + i);
//        }
//        map.put(null, "a");
        System.out.println(map);
        System.out.println("================");

        String key = "a";
        System.out.println(key.hashCode());
        int h;
        System.out.println((h = key.hashCode()) ^ (h >>> 16));

        System.out.println(map.get(key));
    }
}
