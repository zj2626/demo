package com.demo.common.service.thread.volatileKeyword;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Volatile1Test extends MyExcutor {
    private volatile int number = 0;

    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1000);
        excutorPool.futureGet();
        System.out.println("结果 " + number);
    }

    @Override
    public Object doExcute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(1);

        int i = number;
        i++;
        number = i;

        return null;
    }
}
