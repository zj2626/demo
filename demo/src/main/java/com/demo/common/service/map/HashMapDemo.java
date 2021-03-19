package com.demo.common.service.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zj2626
 * @name HashMapDemo
 * @description
 * @create 2021-03-16 16:55
 **/
public class HashMapDemo {

    @Test
    public void testHashMap(){
        Map<String, Object> map = new HashMap<>();
        map.put(null, "aaa");
        map.put("a", "aaa1");
        map.put("b", "aaa2");
        System.out.println(map);
    }
}
