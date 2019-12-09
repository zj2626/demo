package com.demo.common.service.thread.queue;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentQueueDemoProducerConsumer extends MyExcutor {
    ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    //    ConcurrentLinkedDeque<Integer> queue = new ConcurrentLinkedDeque<>();

    @Test
    public void test() throws InterruptedException {
        ThreadDemo producerThread = new ThreadDemo(this);
        producerThread.execute(Params.builder().size(2).build());
        ThreadDemo consumerThread = new ThreadDemo(this);
        consumerThread.execute(Params.builder().size(1).type("2").build());
        producerThread.futureGet();
        consumerThread.futureGet();
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + " reentrantLock produce ---> " + queue.size());
                boolean success = queue.offer(new Random().nextInt(50));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep((new Random().nextInt(20) + 5) * 100);
            Thread.yield();
        }
    }

    @Override
    public Object doExcuteRead(Map<String, Object> parameterignore) throws Exception {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + " reentrantLock consume -> " + queue.size());
                Object obj = queue.poll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep((new Random().nextInt(10) + 5) * 100);
        }
    }
}
