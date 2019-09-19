package com.demo.common.service.thread.reentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Conditon可以实现多路通知和选择性通知 -> 生产者vs消费者
 *
 * @author zhangj
 * @version $Id: ReentrantLockDemoConditionForProducerAndConsumer.java, v 0.1 2019/4/20 20:36 zhangj Exp $
 */
public class ReentrantLockDemoConditionForProducerAndConsumer {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition conditionProducer = reentrantLock.newCondition();
    private static Condition conditionConsumer = reentrantLock.newCondition();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Integer number = 0;
        OptionClass option = new OptionClass(number);


        Producer producer = new Producer(option);
        Consumer consumer = new Consumer(option);

        executor.submit(producer);
        executor.submit(consumer);

    }

    private static class Producer implements Runnable {
        private OptionClass option;

        public Producer(OptionClass option) {
            this.option = option;
        }

        @Override
        public void run() {
            for (; ; ) {
                option.product();
            }
        }
    }

    private static class Consumer implements Runnable {
        private OptionClass option;

        public Consumer(OptionClass option) {
            this.option = option;
        }

        @Override
        public void run() {
            for (; ; ) {
                option.consume();
            }
        }
    }

    private static class OptionClass {
        private Integer number;

        public OptionClass(Integer number) {
            this.number = number;
        }

        public void product() {
            try {
                Thread.sleep(200);

                reentrantLock.lock();

                if (number >= 10) {
                    conditionProducer.await();
                }

                number++;
                System.out.println(System.currentTimeMillis() +  " - 生产 " + number);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conditionConsumer.signalAll();

                reentrantLock.unlock();
            }
        }

        public void consume() {
            try {
                Thread.sleep(500);

                reentrantLock.lock();

                if (number <= 0) {
                    conditionConsumer.await();
                }

                number--;
                System.out.println(System.currentTimeMillis() +  " - 消费 " + number);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conditionProducer.signalAll();

                reentrantLock.unlock();
            }
        }
    }
}
