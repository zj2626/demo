package com.demo.common.service.jvm.oom;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zj2626
 * @name HeapOOM
 * @description 直接内存溢出
 * @create 2021-06-01 22:01
 **/
public class DirectMemoryOOM {

    /**
     * vm args: -Xms20m -Xmx20m -XX:MaxDirectMemorySize=10M -XX:+PrintGCDetails
     * TODO 没抛出溢出
     *
     * java.lang.OutOfMemoryError
     */
    @Test
    public void main() throws IllegalAccessException {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        int i = 0;
        for (; i<50000; i++) {
            System.out.println("第" + i + "次循环开始");
            unsafe.allocateMemory(1024 * 1024);
        }
    }

    /**
     * vm args: -Xms200m -Xmx200m -XX:MaxDirectMemorySize=10M DirectBufferOom -XX:+PrintGCDetails
     *
     * java.lang.OutOfMemoryError: Direct buffer memory
     */
    @Test
    public void main2() throws IllegalAccessException {
        List<ByteBuffer> buffers = new ArrayList<>();
        int i = 0;
        for (; i<50000; i++) {
            i++;
            System.out.println("第" + i + "次循环开始");
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024);
            buffers.add(byteBuffer);
        }
    }
}
