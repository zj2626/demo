package com.demo.common.service.jvm.oom;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zj2626
 * @name HeapOOM
 * @description 运行时常量池溢出
 * @create 2021-06-01 22:01
 **/
public class RuntimeConstantOOM {

    /**
     * 1.6 vm args: -XX:PermSIze=16m -XX:MaxPermSize=16m -Xms256m -Xmx256m -XX:+PrintGCDetails
     * 1.7 vm args: -XX:MetaspaceSize=16m -XX:MaxMetaspaceSize=16m -Xms256m -Xmx256m -XX:+PrintGCDetails
     *
     * 下面代码在1.6会溢出 1.7不会, 因为运行时常量池在1.7已经移动到堆
     */
    @Test
    public void main() throws InterruptedException {
        List<String> result = new ArrayList<>();
        int i=0;
        for (; i<50000; i++) {
            result.add(("" + i).intern());
        }
    }
}
