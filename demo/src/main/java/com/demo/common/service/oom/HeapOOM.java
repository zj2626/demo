package com.demo.common.service.oom;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zj2626
 * @name HeapOOM
 * @description 堆溢出
 * @create 2021-06-01 22:01
 **/
public class HeapOOM {

    /**
    *  vm args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails
    */
    public static void main(String[] args) {
        List<String[]> result = new ArrayList<>();
        int i=0;
        for(;;){
            i++;
            System.out.println("第" + i + "次循环开始");
            result.add(new String[1024*1024]);
            System.out.println("第" + i + "次循环结束");
        }
    }
}
