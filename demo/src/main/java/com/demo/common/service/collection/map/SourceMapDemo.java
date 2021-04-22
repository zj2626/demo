package com.demo.common.service.collection.map;

import com.demo.common.service.collection.map.source.HashMapDemo;
import com.demo.common.service.util.RandomStringUtils;
import org.junit.Test;

public class SourceMapDemo {
    @Test
    public void testDemo() {
        HashMapDemo<Object, Object> map = new HashMapDemo<>(5);
        System.out.println(map);

        map.put(8, null);
        map.put(19, null);
        map.put(29, null);
        map.put(39, null);
        map.put(49, null);
        map.put(59, null);
        map.put(69, null);

//        map.put("4b4_0", null);
//        map.put("25k_1", null);
//        map.put("H47_2", null);
//        map.put("yl6_3", null);
//        map.put("gu5_4", null);
//        map.put("4cm_5", null);
//        map.put("V78_6", null);

//        for (int i = 7; i < 8; i++) {
//            map.put(RandomStringUtils.getRandomString(3) + "_" + i, null);
//        }
        System.out.println("================================================================================================");
        System.out.println("================================");
        System.out.println("================================");
        System.out.println(map);

        map.get(19);
    }

    @Test
    public void testTreeDemo() {

        // 0000 0010 0011 => 67
        // 0000 0110 0011 => 99
        // 0000 1110 0011 => 227
        // 0000 0100 0011 => 131
        // 0000 1100 0011 => 387
        // 0000 1000 0011 => 259
        // 0000 1010 0011 => 291
        // 0001 0000 0011 => 515
        // 0010 0000 0011 => 1027

        // 0000 0001 1111
        // 0000 0000 0011
        System.out.println(67 & 31);
        System.out.println(99 & 31);
        System.out.println(227 & 31);
        System.out.println(1027 & 31);
        // ...

        HashMapDemo<Object, Object> map = new HashMapDemo<>(32);
        System.out.println(map);

        map.put(9, null);
        map.put(29, null);
        map.put(39, null);
        map.put(40, null);
        map.put(60, null);
        map.put(60, null);
        map.put(83, null);
        map.put(89, null);
        map.put(109, null);
        map.put(119, null);
        // 加入8个数据存储到map的数据(数据都在下标为3的位置)
        map.put(99, null);
        map.put(515, null);
        map.put(67, null);
        map.put(227, null);
        map.put(131, null);
        map.put(387, null);
        map.put(259, null);
        map.put(259, null);
        map.put(291, null);
        map.put(1027, null);

        //        for (int i = 7; i < 8; i++) {
        //            map.put(RandomStringUtils.getRandomString(3) + "_" + i, null);
        //        }
        System.out.println("================================================================================================");
        System.out.println("================================");
        System.out.println("================================");
        System.out.println(map);

        map.get(19);
    }

    @Test
    public void demo() {
        System.out.println(19 & 7);
        System.out.println(19 & 8);
        System.out.println(59 & 7);
        System.out.println(59 & 8);
    }
}
