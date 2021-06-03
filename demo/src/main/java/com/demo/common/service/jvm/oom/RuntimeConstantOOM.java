package com.demo.common.service.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zj2626
 * @name HeapOOM
 * @description 堆溢出
 * @create 2021-06-01 22:01
 **/
public class RuntimeConstantOOM {

    /**
     * vm args: -XX:MetaspaceSize=16m -XX:MaxMetaspaceSize=16m -XX:+PrintGCDetails
     */
    public static void main(String[] args) {
        List<String> result = new ArrayList<>();
        for(int i=0;;i++){
            result.add(("" + i).intern());
        }
    }
}
