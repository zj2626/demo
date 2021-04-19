package com.demo.common.service.collection.map;

import com.demo.common.service.collection.map.source.HashMapDemo;
import com.demo.common.service.util.RandomStringUtils;
import org.junit.Test;

public class SourceMapDemo {
    @Test
    public void addTest() {
        HashMapDemo<String, String> map = new HashMapDemo<>(5);
        System.out.println(map);

        map.put("4b4_0", null);
        map.put("25k_1", null);
        map.put("H47_2", null);
        map.put("yl6_3", null);
        map.put("gu5_4", null);
        map.put("4cm_5", null);
        map.put("V78_6", null);
//        for (int i = 7; i < 8; i++) {
//            map.put(RandomStringUtils.getRandomString(3) + "_" + i, null);
//        }
        System.out.println("================================================================================================");
        System.out.println("================================");
        System.out.println("================================");
        System.out.println(map);

        String key = "a";
        System.out.println(map.get(key));
    }
}
