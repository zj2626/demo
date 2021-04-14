package com.demo.common.service.thread.synchronizedLock;

import org.junit.Test;

public class SynchronizedDemo3  {

    private Object lock = new Object();

    @Test
    public void test() throws InterruptedException {
        synchronized(lock){
            synchronized(lock){
                System.out.println("AAAAAAAAAAAAAAAAAAA");
            }
        }
    }
}
