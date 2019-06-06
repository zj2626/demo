package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁:
 * <p>
 * 基于缓存（Redis等）实现分布式锁； ! ***star*** !
 */
public class RedisLockDemo2 implements Runnable {
    private Integer sum = 0;
    private static final String rAtomicName = "demo_atomic";
    private static Redisson redisson = null;


    private static Config config = new Config();

    private static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        try {
//            config.useClusterServers() //这是用的集群server
//                    .setScanInterval(2000) //设置集群状态扫描时间
//                    .setMasterConnectionPoolSize(30000) //设置连接数
//                    .setSlaveConnectionPoolSize(30000)
//                    .addNodeAddress("192.168.1.22:6379", "192.168.1.22:6380")
//                    .setPassword("kdniao2017");
            config.useSentinelServers()
                    .setMasterName("logistics_01")
                    .addSentinelAddress("192.168.1.21:26379")
                    .setConnectTimeout(5000)
                    .setTimeout(5000)
                    .setPassword("kdniao2017");
            redisson = (Redisson) Redisson.create(config);
            //清空自增的ID数字
//            RAtomicLong atomicLong = redisson.getAtomicLong(rAtomicName);
//            atomicLong.set(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Redisson getRedisson() {
        return redisson;
    }

    /**
     * 获取redis中的原子ID
     */
    public static Long nextID() {
        RAtomicLong atomicLong = getRedisson().getAtomicLong(rAtomicName);
        atomicLong.incrementAndGet();
        return atomicLong.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(redisson);
        System.out.println(null != redisson);

        RedisLockDemo2 rd = new RedisLockDemo2();
        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futureTasks.add(service.submit(rd));
        }
        try {
            for (Future<?> futureTask : futureTasks) {
                futureTask.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
        long end = System.currentTimeMillis();

        System.out.println((end - start) + " --- " + rd.sum);
    }

    private static boolean getLock(Redisson redisson, String lockStr) {
        boolean ifLock = false;
        try {
            if (true) {
                long start = System.currentTimeMillis();

                RLock rLock = redisson.getLock(lockStr);
                // lock提供带timeout参数，timeout结束强制解锁，防止死锁
                // (不是等待锁时间而是最大执行时间, 方法会一直阻塞直到获得锁)
                // 不设置则默认设置30s;
                rLock.lock(3000, TimeUnit.MILLISECONDS);
                ifLock = true;

                long end = System.currentTimeMillis();
//                System.out.println(" > > > " + (end - start) + "  " + Thread.currentThread().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ifLock;
    }

    // 当第一个线程超时 则第二个线程进入并设置redis, 然后第一个线程结束并删除redis值(这里存储使用的hash的hkey 并不会影响其他线程,但是会抛异常)
    // 所以不会发生RedisLockDemo类中的问题
    private static void releaseLock(Redisson redisson, String lockStr) {
        RLock rLock = redisson.getLock(lockStr);
        try {
            rLock.unlock();
        } catch (Exception e) {
            System.err.println("由于超时导致该锁已经解除: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String lockStr = "lockStr007";
        System.out.println(lockStr);

        Redisson redisson = getRedisson();

        // option one
        if (true) {
            for (int i = 0; i < 5; i++) {
                try {
                    while (true) {
                        boolean ifLock = RedisLockDemo2.getLock(redisson, lockStr);
                        if (ifLock) {
                            System.out.println(Thread.currentThread().getName() + " A " + i);
                            Integer nt = sum;
                            Thread.sleep(300);
                            sum = nt + 1;
                            System.out.println(Thread.currentThread().getName() + " B " + i);
                            Thread.sleep(200);
                            System.out.println(Thread.currentThread().getName() + " C " + i + "-" + sum);
                            System.out.println(Thread.currentThread().getName() + " *-* " + 0);
                            for (int j = 1; j <= 5; j++) {
                                Thread.sleep(500);
                                System.out.println(Thread.currentThread().getName() + " *-* " + j);
                            }

                            // 当线程等待超时时间设置为3000ms,则上方执行完毕以后在这里等待的10ms处发生超时,此时第二个线程马上获得锁进入
                            // 所以第二个线程的"thread-A 0"会在第一个线程的"thread-release"日志前打印出来(实际情况可能有微小误差,因为线程执行开始时间不定)
                            Thread.sleep(10);

                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        System.out.println(Thread.currentThread().getName() + " release " + "\n");
                        releaseLock(redisson, lockStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // option two
        if (false) {
            System.out.println("3---" + Thread.currentThread().getName() + " begin");
            for (int j = 0; j < 100; j++) {
                try {
                    while (true) {
                        boolean ifLock = RedisLockDemo2.getLock(redisson, lockStr);
                        if (ifLock) {
                            System.out.println(Thread.currentThread().getName() + " - " + sum);
                            sum++;

                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        releaseLock(redisson, lockStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);
        }
    }
}
