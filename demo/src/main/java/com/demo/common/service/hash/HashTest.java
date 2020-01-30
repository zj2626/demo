package com.demo.common.service.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HashTest {

    /**
     * 1. 随机的字符串进行hash
     * 2. 首先设置容器个数为4, 对hash后的值进行取余 记录结果作为存储位置
     * 3. 进行倍数扩容,然后同上
     * 4. 对比前后的位置不同
     */
    @Test
    public void test() {
        String[] info = {"aa1", "bb2", "cc3", "dd4", "ee5", "ff6", "gg7", "xx8", "yy9", "zz0",
                "#1", "$1", "fewf", "21s", "g", "sge", "e2f22", "h45", "aasw", "e2ef", "f4r3",
                "55hh", "a2cw3", "f2", "agh", "f;l", "s0di", "v4h3", "fj48", "fe2", "ff2", "fedsla2",
                "vwmi", "fehwu", "vfjir", "hkhk", "ssa", "fwwji"};

        Map<String, String> before = new HashMap<>(100);
        for(String inf : info){
            before.put(inf, String.format("%2d", inf.hashCode() % 8));
        }
        System.out.println(before);

        Map<String, String> after = new HashMap<>(100);
        for(String inf : info){
            after.put(inf, String.format("%2d", inf.hashCode() % 32));
        }
        System.out.println(after);

        int same = 0;
        for(String key : before.keySet()){
            if(before.get(key).equals(after.get(key))){
                same++;
            }
        }

        System.out.println(before.size());
        System.out.println(same);
    }
}
