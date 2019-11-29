package com.demo.common.service.thread.redislock;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.config.Config;

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

    static {
        try {
//            config.useClusterServers() //这是用的集群server
//                    .setScanInterval(2000) //设置集群状态扫描时间
//                    .setMasterConnectionPoolSize(30000) //设置连接数
//                    .setSlaveConnectionPoolSize(30000)
//                    .addNodeAddress("redis://192.168.1.22:6379", "redis://192.168.1.22:6380")
//                    .setPassword("123456");

//            config.useSentinelServers()
//                    .setMasterName("logistics_01")
//                    .addSentinelAddress("redis://192.168.1.21:26379")
//                    .setConnectTimeout(5000)
//                    .setTimeout(5000)
//                    .setPassword("123456");

            config.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379")
                    .setConnectTimeout(5000)
                    .setTimeout(5000)
                    .setPassword("123456");

            redisson = (Redisson) Redisson.create(config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RedisLockDemo2 rd = new RedisLockDemo2();
        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newFixedThreadPool(30);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
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

    private static boolean getLock(String lockStr) {
        boolean ifLock = false;
        try {
            RLock rLock = redisson.getLock(lockStr);
            if (false) {
                // lock提供带timeout参数，timeout结束强制解锁，防止死锁 (不是等待锁时间而是最大执行时间, 方法会一直阻塞直到获得锁)
                // 不设置则默认设置30s;

                rLock.lock(5, TimeUnit.SECONDS);
                ifLock = true;
            }

            if (true) {
                // 即使该锁是公平锁fairLock，使用tryLock()的方式获取锁也会是非公平的方式，只要获取锁时该锁可用那么就会直接获取并返回true。
                // 这种直接插入的特性在一些特定场景是很有用的。但是如果就是想使用公平的方式的话，可以试一试tryLock(0, TimeUnit.SECONDS)，几乎跟公平锁没区别，只是会监测中断事件
                /*
                 * 在waitTime内 重复尝试获取锁 直到超过等待时间或成功获取锁(等待获得锁总时长:waitTime;每次获取锁时间:leaseTime)
                 */
                ifLock = rLock.tryLock(5, 3, TimeUnit.SECONDS);
                // ifLock = rLock.tryLock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ifLock;
    }

    private static void releaseLock(Redisson redisson, String lockStr) {
        RLock rLock = redisson.getLock(lockStr);
        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }

    @Override
    public void run() {
        String lockStr = "lockStr007";
        System.out.println(lockStr);

        try {
            boolean ifLock = RedisLockDemo2.getLock(lockStr);
            // total: 1000ms=1s
            if (ifLock) {
                System.out.println(Thread.currentThread().getName() + " A ");
                Integer nt = sum;
                Thread.sleep(200);
                sum = nt + 1;
                System.out.println(Thread.currentThread().getName() + " B ");
                Thread.sleep(200);
                for (int j = 1; j <= 4; j++) {
                    Thread.sleep(100);
                    System.out.print("*" + j + "\t");
                }
                System.out.println();
                System.out.println(Thread.currentThread().getName() + " C " + "-------------------> " + sum);
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName() + " D ");
            } else {
                System.out.println(Thread.currentThread().getName() + " - E");
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

    /**
     * 获取redis中的原子ID
     */
    public static Long nextID() {
        RAtomicLong atomicLong = redisson.getAtomicLong(rAtomicName);
        atomicLong.incrementAndGet();
        return atomicLong.get();
    }
}
