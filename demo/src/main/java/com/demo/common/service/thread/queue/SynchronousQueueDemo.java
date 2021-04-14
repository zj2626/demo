package com.demo.common.service.thread.queue;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo extends MyExcutor {
    SynchronousQueue<Integer> queue = new SynchronousQueue<>();

    /**
     * SynchronousQueue没有容量，是无缓冲等待队列，是一个不存储元素的阻塞队列，会直接将任务交给消费者，
     * 1.必须等队列中的添加元素被消费后才能继续添加新的元素。
     * 2.队列中没有元素则拉取队列进入阻塞状态直到有新的元素
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo producerThread = new ExcutorPoolDemo(this);
        producerThread.execute(Params.builder().size(1).build());
        ExcutorPoolDemo consumerThread = new ExcutorPoolDemo(this);
//        consumerThread.execute(Params.builder().size(1).type("doExcuteRead").build());
        producerThread.futureGet();
        consumerThread.futureGet();
    }

    @Override
    public Object doExcute() throws Exception {
        while (true) {
            try {
                boolean success = true;
//                success = queue.offer(new Random().nextInt(50), 5, TimeUnit.SECONDS);
                queue.put(new Random().nextInt(50));
                System.out.println(Thread.currentThread().getName() + " produce ---> " + queue.size() + " ==== " + success);
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
                Object obj = null;
                obj = queue.poll();
//                obj = queue.take();
                System.out.println(Thread.currentThread().getName() + " consume -> " + queue.size() + " ==== " + obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep((new Random().nextInt(10) + 5) * 100);
        }
    }
}
