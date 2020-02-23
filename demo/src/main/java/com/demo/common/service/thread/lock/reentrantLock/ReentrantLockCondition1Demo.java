package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCondition1Demo extends MyExcutor {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static int count = 0;

    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo aThread = new ExcutorPoolDemo(this);
        aThread.execute(Params.builder().size(1).build());
        ExcutorPoolDemo bThread = new ExcutorPoolDemo(this);
        bThread.execute(Params.builder().size(1).type("2").build());
        aThread.futureGet();
        bThread.futureGet();
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        while (true) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " reentrantLock a>>> getLock");
                count++;
                Thread.sleep(500);
                if (count % 2 == 0) {
                    // 执行signal()[相当于notify()]进行唤醒
                    conditionB.signal();
                    // 执行await()休眠线程，要求线程持有相关的锁，当线程调用condition.await()之后，这个线程会释放持有的锁，并进入等待状态[相当于wait()];
                    conditionA.await();
                    System.out.println(Thread.currentThread().getName() + " reentrantLock a>>> info");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                    System.out.println(Thread.currentThread().getName() + " reentrantLock a>>> unLock");
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public Object doExcuteRead(Map<String, Object> parameterignore) throws Exception {
        while (true) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " reentrantLock b getLock");
                Thread.sleep(2000);
                conditionA.signal();
                conditionB.await();
                System.out.println(Thread.currentThread().getName() + " reentrantLock b info");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                    System.out.println(Thread.currentThread().getName() + " reentrantLock b unLock");
                    lock.unlock();
                }
            }
        }
    }
}
