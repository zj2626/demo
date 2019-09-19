package com.demo.common.service.jvm;

import java.util.HashMap;
import java.util.Map;

// VM: -Xmx512M -Xms512M -XX:+UseSerialGC -Xloggc:E:/gc.log -XX:+PrintGCDetails -Xmn1m -XX:PretenureSizeThreshold=50 -XX:MaxTenuringThreshold=1
public class GcTest {
    private static Map<Long, Byte[]> map = new HashMap<>();

    public static void main(String[] args) {
        try {
            while (true) {
                if (map.size() * 512 / 1024 / 1024 >= 450) {
                    System.out.println("清理 before----" + map.size());
                    map.clear();
                }

                for (int i = 0; i < 1024; i++) {
                    map.put(System.nanoTime(), new Byte[512]);
                }
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
