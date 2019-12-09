package com.demo.common.service.thread.queue;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueDemo  extends MyExcutor {
    LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();

    /**
     * 队列LinkedTransferQueue和SynchronousQueue有些类似，
     * 但LinkedTransferQueue可以使用put、tryTransfer、transfer添加多个数据而不用等别的线程来获取。
     * tryTransfer和transfer与put不同的是，tryTransfer和transfer可以检测是否有线程在等待获取数据，
     * 如果检测到就立即发送新增的数据给这个线程获取而不用放入队列。
     * 所以当使用tryTransfer和transfer往LinkedTransferQueue添加多个数据的时候，添加一个数据后，会先唤醒等待的获取数据的线程，再继续添加数据。
     * 1.tryTransfer会立即返回 tryTransfer(timeout)超时等待 transfer阻塞
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        ThreadDemo producerThread = new ThreadDemo(this);
        producerThread.execute(Params.builder().size(1).build());
        ThreadDemo consumerThread = new ThreadDemo(this);
        consumerThread.execute(Params.builder().size(1).type("2").build());
        producerThread.futureGet();
        consumerThread.futureGet();
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        while (true) {
            try {
                boolean success = true;
                // queue.put(new Random().nextInt(50));
                queue.transfer(new Random().nextInt(50));
                System.out.println(Thread.currentThread().getName() + " produce ---> " + queue.size() + " ==== " + success);
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
                Object obj = queue.take();
                System.out.println(Thread.currentThread().getName() + " consume -> " + queue.size() + " ==== " + obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep((new Random().nextInt(10) + 5) * 100);
        }
    }
}
