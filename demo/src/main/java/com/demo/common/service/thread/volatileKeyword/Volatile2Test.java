package com.demo.common.service.thread.volatileKeyword;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Volatile2Test extends MyExcutor {
//    private boolean ifRun = true;
    private volatile boolean ifRun = true;
    private int number = 0;

    @Test
    public void test() throws Exception {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(2);

        stopThread();


        excutorPool.futureGet();
        System.out.println("结果 " + ifRun + " - " + number);
    }

    @Override
    public Object doExcute() throws Exception {
        while (ifRun) {
            int i = number;
            i++;
            number = i;
        }
        return null;
    }

    private void stopThread() throws Exception {
        TimeUnit.MILLISECONDS.sleep(10);
        ifRun = false;
    }
}
