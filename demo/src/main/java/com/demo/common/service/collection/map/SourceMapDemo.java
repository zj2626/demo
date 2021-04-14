package com.demo.common.service.collection.map;

import com.demo.common.service.collection.map.source.HashMapDemo;
import com.demo.common.service.util.RandomStringUtils;
import org.junit.Test;

public class SourceMapDemo {
    @Test
    public void addTest() {
        HashMapDemo<String, String> map = new HashMapDemo<>(5);
        System.out.println(map);
        map.put(RandomStringUtils.getRandomString(3), null);
        System.out.println(map);
        map.put(RandomStringUtils.getRandomString(3), null);
        map.put(RandomStringUtils.getRandomString(3), null);
        map.put(RandomStringUtils.getRandomString(3), null);
        map.put(RandomStringUtils.getRandomString(3), null);
        map.put(RandomStringUtils.getRandomString(3), null);
        System.out.println(map);
        map.put(RandomStringUtils.getRandomString(3), null);
        System.out.println(map);

        for (int i = 0; i < 40; i++) {
            map.put(RandomStringUtils.getRandomString(3) + i, null);
        }
//        map.put(null, null);
        System.out.println(map);
        System.out.println("================");

        String key = "a";
        System.out.println(key.hashCode());
        int h;
        System.out.println((h = key.hashCode()) ^ (h >>> 16));

        System.out.println(map.get(key));
    }
}
