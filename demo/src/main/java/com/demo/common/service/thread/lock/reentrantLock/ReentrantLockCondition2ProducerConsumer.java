package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCondition2ProducerConsumer extends MyExcutor {
    private static int count = 0;

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionProducer = lock.newCondition();
    private static Condition conditionConsumer = lock.newCondition();

    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo producerThread = new ExcutorPoolDemo(this);
        producerThread.execute(Params.builder().size(7).build());
        ExcutorPoolDemo consumerThread = new ExcutorPoolDemo(this);
        consumerThread.execute(Params.builder().size(5).type("2").build());
        producerThread.futureGet();
        consumerThread.futureGet();
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        while (true) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " reentrantLock produce ---> " + count);
                Thread.sleep(500);
                if (count >= 0) {
                    conditionConsumer.signalAll();
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
    public Object doExcuteRead(Map<String, Object> parameterignore) throws Exception {
        while (true) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " reentrantLock consume -> " + count);
                Thread.sleep(500);
                if (count <= 10) {
                    conditionProducer.signalAll();
                }
                if (count <= 0) {
                    conditionConsumer.await();
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
