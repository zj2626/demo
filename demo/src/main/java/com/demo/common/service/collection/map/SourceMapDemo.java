package com.demo.common.service.collection.map;

import com.demo.common.service.collection.map.source.HashMapDemo;
import org.junit.Test;

public class SourceMapDemo {
    @Test
    public void addTest() {
        HashMapDemo<String, String> map = new HashMapDemo<>(9);
        map.put(null, "a");
        map.put("a", "aaa");
        map.put("f", "fff");
        map.put("e", "eee");
        map.put("b", "bbb");
        System.out.println(map);
        System.out.println("================");

        String key = "a";
        System.out.println(key.hashCode());
        int h;
        System.out.println((h = key.hashCode()) ^ (h >>> 16));

        System.out.println(map.get(key));
    }
}
