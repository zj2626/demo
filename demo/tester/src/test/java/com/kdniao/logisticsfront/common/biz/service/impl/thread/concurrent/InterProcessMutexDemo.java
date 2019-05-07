package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.apache.curator.RetryPolicy;
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
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁:
 * <p>
 * 基于数据库实现分布式锁；
 * 基于缓存（Redis等）实现分布式锁；
 * 基于Zookeeper实现分布式锁； ! ***star*** !
 */
public class InterProcessMutexDemo implements Runnable {
    private Integer sum = 0;
    private boolean ifBlock;
    private String taskName;

    public static final String LOCK_ZNODE_FUCK = "/fuck1";
    public static final String LOCK_ZNODE_SHIT = "/shit1";

    public static CuratorFramework client;

    public InterProcessMutexDemo(boolean ifBlock) {
        this.ifBlock = ifBlock;
    }

    static {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        client = CuratorFrameworkFactory.builder()
                .connectString("192.168.1.230:2181")    // zk的server地址，多个server之间使用英文逗号分隔开
                .connectionTimeoutMs(30000)             // 连接超时时间 默认是15s
                .sessionTimeoutMs(60000)                // 会话超时时间 默认是60s
                .retryPolicy(retryPolicy)               // 重试
                .build();

        client.start();
    }

    public static void main(String[] args) throws InterruptedException {
        InterProcessMutexDemo rd = new InterProcessMutexDemo(true);

        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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

//        client.close();
        CloseableUtils.closeQuietly(client);
    }

    @Override
    public void run() {
        String lockStr = (Thread.currentThread().getName().contains("2") || Thread.currentThread().getName().contains("4"))
                ? LOCK_ZNODE_SHIT : LOCK_ZNODE_FUCK;    // 两个path
        lockStr = LOCK_ZNODE_FUCK;                    // 一个path
        System.out.println(lockStr);

        // 锁对象 client 锁节点
        InterProcessMutex lock = new InterProcessMutex(client, lockStr);

        // option one
//        try {
//            lock.acquire();
////            lock.acquire();
////            lock.acquire();
////            lock.acquire();
////            lock.acquire();
////            lock.acquire();
////            lock.acquire();
////            lock.acquire();
//
//            for (int i = 0; i < 5; i++) {
//                System.out.println(Thread.currentThread().getName() + " A " + i);
//                Integer nt = sum;
//                Thread.sleep(100);
//                sum = nt + 1;
//                System.out.println(Thread.currentThread().getName() + " B " + i);
//                Thread.sleep(200);
//                if (ifBlock && i == 2) {
//
//                }
//                System.out.println(Thread.currentThread().getName() + " C " + i + "-" + sum);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//            try {
//                if (lock.isAcquiredInThisProcess()) {
//                    System.out.println(Thread.currentThread().getName() + " release " + "\n");
//                    lock.release();
////                    lock.release();
////                    lock.release();
////                    lock.release();
////                    lock.release();
////                    lock.release();
////                    lock.release();
////                    lock.release();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }

        // option two
//        for (int i = 0; i < 5; i++) {
//            try {
//                while (true) {
//                    boolean ifLock = lock.acquire(0, TimeUnit.MILLISECONDS);
//                    if (ifLock) {
//                        System.out.println(Thread.currentThread().getName() + " A " + i);
//                        Integer nt = sum;
//                        Thread.sleep(300);
//                        sum = nt + 1;
//                        System.out.println(Thread.currentThread().getName() + " B " + i);
//                        Thread.sleep(600);
//                        if (ifBlock && i == 2) {
//
//                        }
//                        System.out.println(Thread.currentThread().getName() + " C " + i + "-" + sum);
//
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (lock.isAcquiredInThisProcess()) {
//                        System.out.println(Thread.currentThread().getName() + " release " + "\n");
//                        lock.release();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // option three
//        try {
//            lock.acquire();
////                boolean ifAcquire = lock.acquire(0, TimeUnit.MILLISECONDS);
//            System.out.println("2---" + Thread.currentThread().getName() + " begin");
//            for (int j = 0; j < 100000; j++) {
//                sum++;
//            }
//            System.out.println("2---" + Thread.currentThread().getName() + " done " + sum);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                lock.release();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        // option four
        System.out.println("3---" + Thread.currentThread().getName() + " begin");
        for (int j = 0; j < 1000; j++) {
            try {
                lock.acquire();
//                boolean ifAcquire = lock.acquire(0, TimeUnit.MILLISECONDS);
                sum++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);
    }
}
