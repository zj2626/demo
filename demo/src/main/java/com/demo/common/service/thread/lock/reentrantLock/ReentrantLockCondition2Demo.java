package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCondition2Demo extends MyExcutor {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionProducer = lock.newCondition();
    private static Condition conditionConsumer = lock.newCondition();
    private static int count = 0;

    @Test
    public void test() throws InterruptedException {
        ThreadDemo producerThread = new ThreadDemo(this);
        producerThread.execute(Params.builder().size(5).build());
        ThreadDemo consumerThread = new ThreadDemo(this);
        consumerThread.execute(Params.builder().size(5).type("2").build());
        producerThread.futureGet();
        consumerThread.futureGet();
    }

    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        while (true) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " reentrantLock produce ---> " + count);
                Thread.sleep(500);
                if (count >= 0) {
                    conditionConsumer.signal();
                }
                if (count >= 10) {
                    conditionProducer.await();
                }
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
            Thread.sleep(500);
        }
    }

    @Override
    public String doExcuteRead(Map<String, String> ignore) throws Exception {
        while (true) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " reentrantLock consume -> " + count);
                Thread.sleep(500);
                if (count <= 0) {
                    conditionConsumer.await();
                }
                if (count <= 10) {
                    conditionProducer.signal();
                }
                count--;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
            Thread.sleep(500);
        }
    }
}
