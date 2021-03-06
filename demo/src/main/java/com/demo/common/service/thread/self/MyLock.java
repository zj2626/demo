package com.demo.common.service.thread.self;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock {
    private MySync mySync = new MySync();

    @Override
    public void lock() {
        mySync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        mySync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return mySync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return mySync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        mySync.release(1);
    }

    @Override
    public Condition newCondition() {
        return mySync.newCondition();
    }

    private class MySync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            // 获取当前state 0表示没有线程占用锁 >0表示有
            int state = getState();
            if (getExclusiveOwnerThread() == Thread.currentThread()) {
                setState(state + arg);
                return true;
            } else if (state == 0) {
                if (compareAndSetState(0, arg)) {
                    // 修改成功
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            int state = getState();
            // 只有获得锁的线程能进入
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                return false;
            } else if (state > 0) {
                state = state - arg;
                if (0 == state) {
                    setExclusiveOwnerThread(null);
                }
            }
            setState(state);
            return true;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }
}
