package com.demo.common.service.thread.abs;

public interface LockInterface {

    boolean getLock() throws Exception;

    void releaseLock();
}
