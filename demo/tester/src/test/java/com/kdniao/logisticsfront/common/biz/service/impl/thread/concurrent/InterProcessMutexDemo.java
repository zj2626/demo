package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 信号量：Semaphore  :信号量允许多个线程同时访问同一个资源
 */
public class InterProcessMutexDemo implements Runnable {
    private Integer sum = 0;
    private boolean ifBlock;
    private String taskName;

    public static final String LOCK_ZNODE_FUCK = "/fuck1";
    public static final String LOCK_ZNODE_SHIT = "/shit1";

    public static CuratorFramework client;

    public static CuratorFrameworkFactory.Builder builder;

    public InterProcessMutexDemo(boolean ifBlock) {
        this.ifBlock = ifBlock;
    }

    static {
        client = CuratorFrameworkFactory.newClient("192.168.1.230:2181", new ExponentialBackoffRetry(1000, 3));
        builder = CuratorFrameworkFactory.builder().connectString("192.168.1.230:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3));
    }

    public static void main(String[] args) throws InterruptedException {
        InterProcessMutexDemo rd = new InterProcessMutexDemo(true);

        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            futureTasks.add(service.submit(rd));
            Thread.sleep(1);
        }
        try {
            for (Future<?> futureTask : futureTasks) {
                futureTask.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();

        System.out.println(rd.sum);
    }

    @Override
    public void run() {
        String lockStr = (Thread.currentThread().getName().contains("2") || Thread.currentThread().getName().contains("4"))
                ? LOCK_ZNODE_SHIT : LOCK_ZNODE_FUCK;
//        lockStr = LOCK_ZNODE_FUCK;
        System.out.println(lockStr);

        CuratorFramework client = builder.build();
        client.start();
        // 锁对象 client 锁节点
        InterProcessMutex lock = new InterProcessMutex(client, lockStr);

        // option one
//        try {
//            lock.acquire();
//
//            for (int i = 0; i < 5; i++) {
//                System.out.println(Thread.currentThread().getName() + " A " + i);
//                Thread.sleep(300);
//                sum++;
//                System.out.println(Thread.currentThread().getName() + " B " + i);
//                Thread.sleep(600);
//                if (ifBlock && i == 2) {
//
//                }
//                System.out.println(Thread.currentThread().getName() + " C " + i + "-" + sum);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println(Thread.currentThread().getName() + " release " + "\n");
//
//            try {
//                if (lock.isAcquiredInThisProcess()) {
//                    lock.release();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        // option two
        try {
            lock.acquire();
            System.out.println("2---" + Thread.currentThread().getName() + " begin");
            for (int j = 0; j < 100000; j++) {
                sum++;
            }
            System.out.println("2---" + Thread.currentThread().getName() + " done " + sum);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // option three  ?????
//        System.out.println("3---" + Thread.currentThread().getName() + " begin");
//        for (int j = 0; j < 100000; j++) {
//            try {
//                lock.acquire();
//                sum++;
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    lock.release();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);

        CloseableUtils.closeQuietly(client);
    }
}
