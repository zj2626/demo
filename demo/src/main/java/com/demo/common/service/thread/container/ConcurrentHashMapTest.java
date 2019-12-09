package com.demo.common.service.thread.container;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest extends MyExcutor {
    private static Map<String, String> map = null;

    static {
//        map = new HashMap<>(20);
                map = new ConcurrentHashMap<>(20);
    }

    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(500);
        threadExcutor.futureGet();
        System.out.println("ConcurrentHashMap 结果等于 1000; HashMap 结果不一定等于");
        System.out.println(map.size());
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        // useless
        for (int i = 0; i < 1000; i++) {
            map.put(Integer.toString(i), String.valueOf(Math.random() * 100));
        }
        return null;
    }

    @Test
    public void test2() {
        /*
        0.默认容量为16, 负载因子为 0.75
        1.扩充行为(resize)发生在当元素个数超过12,24,48...时, 即(16*0.75, 32*0.75, 64*0.75 ...)
        2.当设置初始化大小(initialCapacity),扩充位置不变
        3.当设置 initialCapacity=40,第一次扩充发生在元素个数大于24的位置,每次扩大一倍
        4.最好设置initialCapacity为16,32,64...
         */
        Map<String, String> aa = new HashMap<>(40);
        for (int i = 0; i < 40; i++) {
            aa.put("a" + i, "b");
            System.out.println(i);
        }
    }
}
