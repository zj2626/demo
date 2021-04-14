package com.demo.common.service.thread.queue;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueDemoProducerConsumer extends MyExcutor {
    //    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
    //    BlockingQueue<Integer> queue = new LinkedBlockingDeque<>(10);

    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo producerThread = new ExcutorPoolDemo(this);
        producerThread.execute(Params.builder().size(2).build());
        ExcutorPoolDemo consumerThread = new ExcutorPoolDemo(this);
        consumerThread.execute(Params.builder().size(1).type("doExcuteRead").build());
        producerThread.futureGet();
        consumerThread.futureGet();
    }

    @Override
    public Object doExcute() throws Exception {
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
    public Object doExcuteRead() throws Exception {
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
