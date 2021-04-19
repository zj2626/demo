package com.demo.common.service.thread.volatileKeyword;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import org.junit.Test;

public class Volatile3Test extends MyExcutor {
    private static volatile long number = 0;

    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(2);

        excutorPool.futureGet();
        System.out.println("结果 " + " - " + number);
    }

    @Override
    public Object doExcute() throws Exception {
        System.out.println("ADD...");
        int n=0;
        while (number <= 100000) {
            number++;
            n++;
        }
        System.out.println(Thread.currentThread().getName() + " --- " + n);
        return null;
    }
}
