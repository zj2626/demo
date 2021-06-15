package com.demo.common.service.jvm.oom;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zj2626
 * @name HeapOOM
 * @description 堆溢出
 * @create 2021-06-01 22:01
 **/
public class HeapOOM {

    /**
     * vm args: -Xms16m -Xmx16m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:NewRatio=7
     */
    @Test
    public void tetStringSize() throws InterruptedException {
        Thread.sleep(10000);
        List<byte[]> result = new ArrayList<>();
        int i = 0;
        for (; ; ) {
            i++;
            System.out.println("------------ " + i);
            result.add(new byte[1024 * 1024]);
            Thread.sleep(2000);
        }
    }

    /**
     * vm args: -Xms20m -Xmx20m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
     * <p>
     * PSYoungGen
     */
    @Test
    public void main() throws InterruptedException {
        System.out.println("\n\n");
        Thread.sleep(30000);
        List<byte[]> result = new ArrayList<>();
        int i = 0;
        for (; i < 50000; i++) {
            System.out.println("第" + i + "次循环开始");
            result.add(new byte[1024 * 1024]);
        }

        System.out.println("before system gc");
        System.gc();
        System.out.println("after system gc");
    }

    /**
     * vm args: -Xms32m -Xmx32m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:+UseSerialGC
     * 使用Serial+Serial Old收集器组合进行垃圾收集
     * <p>
     */
    @Test
    public void UseSerialGC() throws InterruptedException {
        main();
    }


    /**
     * vm args: -Xms32m -Xmx32m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParNewGC
     * <p>
     */
    @Test
    public void UseParNewGC() throws InterruptedException {
        main();
    }

    /**
     * vm args: -Xms32m -Xmx32m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:+UseConcMarkSweepGC
     * <p>
     */
    @Test
    public void UseConcMarkSweepGC() throws InterruptedException {
        main();
    }

    /**
     * vm args: -Xms32m -Xmx32m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParallelGC
     * <p>
     */
    @Test
    public void UseParallelGC() throws InterruptedException {
        main();
    }

    /**
     * vm args: -Xms32m -Xmx32m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParallelOldGC
     * <p>
     */
    @Test
    public void UseParallelOldGC() throws InterruptedException {
        main();
    }
}
