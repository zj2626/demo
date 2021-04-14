package com.demo.common.service.thread.lock;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import org.junit.Test;

import java.util.Map;

public class SynchronizedDemo2 extends MyExcutor {
    @Test
    public void testUnfair() throws InterruptedException {
        ExcutorPoolDemo aThread = new ExcutorPoolDemo(this);
        aThread.execute(Params.builder().size(1).build());
        ExcutorPoolDemo bThread = new ExcutorPoolDemo(this);
        bThread.execute(Params.builder().size(1).type("doExcuteRead").build());
        aThread.futureGet();
        bThread.futureGet();
    }

    /* 公平锁 */
    @Override
    public Object doExcute() throws Exception {
        classSync();
        return null;
    }

    /* 非公平锁 */
    @Override
    public Object doExcuteRead() throws Exception {
        objectSync();
        return null;
    }

    private void objectSync() throws InterruptedException {
        Thread.sleep(2);

        synchronized (this) {
            System.out.println("同步代码1");
            Thread.sleep(2000);
            System.out.println("同步代码1 out");
        }
    }

    private void classSync() throws InterruptedException {
        synchronized (SynchronizedDemo2.class) {
            System.out.println("同步代码2");
            Thread.sleep(2000);
            System.out.println("同步代码2 out");
        }
    }
}
