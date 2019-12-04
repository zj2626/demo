package com.demo.common.service.thread.abs;

public class DefaultLock implements LockInterface {
    @Override
    public boolean getLock() {
        return true;
    }

    @Override
    public void releaseLock() { }
}
