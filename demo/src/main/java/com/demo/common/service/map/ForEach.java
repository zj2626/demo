package com.demo.common.service.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ForEach {
    static Map<String, Object> map = new HashMap<>();

    static {
        for (int i = 0; i < 10000000; i++) {
            map.put("abc" + i, i);
        }
    }

    @Test
    public void test1() {
        System.out.println(map.size());
        long start = System.currentTimeMillis();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        test2();
        test3();
        test4();
    }

    @Test
    public void test2() {
        System.out.println(map.size());
        long start = System.currentTimeMillis();
        for (String key : map.keySet()) {
            String k = key;
            Object v = map.get(key);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void test3() {
        System.out.println(map.size());
        long start = System.currentTimeMillis();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String k = entry.getKey();
            Object v = entry.getValue();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void test4() {
        System.out.println(map.size());
        long start = System.currentTimeMillis();
        map.forEach((key, value) -> {
            String k = key;
            Object v = value;
        });

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
