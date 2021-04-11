package com.demo.common.service.collection.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zj2626
 * @name HashMapDemo
 * @description
 * @create 2021-03-16 16:55
 **/
public class MapDemo {

    @Test
    public void testHashMap(){
        Map<String, Object> map = new HashMap<>();
        map.put(null, "a");
        map.put("a", "aaa");
        map.put("f", "fff");
        map.put("e", "eee");
        map.put("b", "bbb");
        System.out.println(map);
    }
}
