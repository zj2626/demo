package com.demo.common.service.thread.redislock;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RedisLockDemo3 implements Runnable {
    private Integer sum = 0;

    private static RedissonClient redissonClient = null;

    static {
        try {
            Config config = new Config();

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

            redissonClient = Redisson.create(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RedisLockDemo3 rd = new RedisLockDemo3();
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
            RLock rLock = redissonClient.getLock(lockStr);
            /*
             * 在waitTime内 重复尝试获取锁 直到超过等待时间或成功获取锁(等待获得锁总时长:waitTime;每次获取锁时间:leaseTime)
             */
            ifLock = rLock.tryLock(5, 3, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ifLock;
    }

    private static void releaseLock(RedissonClient redissonClient, String lockStr) {
        RLock rLock = redissonClient.getLock(lockStr);
        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }

    @Override
    public void run() {
        String lockStr = "lockStr007";
        System.out.println(lockStr);

        try {
            boolean ifLock = RedisLockDemo3.getLock(lockStr);
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
                releaseLock(redissonClient, lockStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
