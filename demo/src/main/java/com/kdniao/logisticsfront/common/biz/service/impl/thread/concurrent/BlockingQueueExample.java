package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueExample {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(1024);
        Provider provider = new Provider(blockingQueue);
        Consumer consumer = new Consumer(blockingQueue);

        new Thread(provider).start();
        new Thread(consumer).start();

        Thread.sleep(60000);
    }


    static class Provider implements Runnable {
        private ArrayBlockingQueue queue;

        public Provider(ArrayBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                queue.put("1");
                System.out.println("put" + 1);
                Thread.sleep(1000);
                queue.put("2");
                System.out.println("put" + 2);
                Thread.sleep(2000);
                queue.put("3");
                System.out.println("put" + 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable {
        private ArrayBlockingQueue queue;

        public Consumer(ArrayBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                System.out.println("take" + queue.take());
                System.out.println("take" + queue.take());
                System.out.println("take" + queue.take());

                /*********/
                queue.take();
                queue.poll();
                queue.remove();
                /*********/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

