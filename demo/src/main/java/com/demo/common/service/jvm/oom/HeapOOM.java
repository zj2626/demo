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
     * vm args: -Xms32m -Xmx32m -Xmn8m -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps
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
     * vm args: -Xms32m -Xmx32m -Xmn8m -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps
     * <p>
     * PSYoungGen
     */
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
        List<byte[]> result = new ArrayList<>();
        int i = 0;
        for (; i < 50000; i++) {
            Thread.sleep(2000);
            System.out.println("第" + i + "次循环开始");
            result.add(new byte[1024 * 1024]);
        }

        System.gc();
    }

    private void fun() throws InterruptedException {
        System.out.println("[beginning...]\n\n");
        Thread.sleep(10000);
        List<byte[]> result = new ArrayList<>();
        int i = 0;
        for (; i < 50000; i++) {
            System.out.println("第" + i + "次循环开始");
            for (int j = 0; j < 1024; j++) {
                result.add(new byte[1024]);
            }
            Thread.sleep(500);
        }

        System.out.println("before system gc");
        System.gc();
        System.out.println("after system gc");
    }

    /**
     * vm args: -Xms32m -Xmx32m -Xmn8m -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseSerialGC
     * <p>
     * 使用Serial+Serial Old收集器组合进行垃圾收集
     */
    @Test
    public void UseSerialGC() throws InterruptedException {
        fun();
    }


    /**
     * vm args: -Xms32m -Xmx32m -Xmn8m -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseParNewGC
     * <p>
     * 使用ParNew + Serial Old的收集器进行垃圾回收
     */
    @Test
    public void UseParNewGC() throws InterruptedException {
        fun();
    }

    /**
     * vm args: -Xms32m -Xmx32m -Xmn8m -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseConcMarkSweepGC
     * <p>
     * 使用ParNew + CMS +  Serial Old的收集器组合进行内存回收，Serial Old作为CMS出现“Concurrent Mode Failure”失败后的后备收集器使用。
     */
    @Test
    public void UseConcMarkSweepGC() throws InterruptedException {
        fun();
    }

    /**
     * vm args: -Xms32m -Xmx32m -Xmn8m -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseParallelGC
     * <p>
     * 使用Parallel Scavenge + Serial Old的收集器组合进行回收 (默认)
     */
    @Test
    public void UseParallelGC() throws InterruptedException {
        fun();
    }

    /**
     * vm args: -Xms32m -Xmx32m -Xmn8m -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseParallelOldGC
     * <p>
     * 使用Parallel Scavenge + Parallel Old的收集器组合进行回收
     */
    @Test
    public void UseParallelOldGC() throws InterruptedException {
        fun();
    }

    /**
     * vm args: -Xms32m -Xmx32m -Xmn8m -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseG1GC
     * <p>
     * 使用Parallel Scavenge + Parallel Old的收集器组合进行回收
     */
    @Test
    public void UseG1GC() throws InterruptedException {
        fun();
    }
}
