package com.demo.common.service.thread.lock;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

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
