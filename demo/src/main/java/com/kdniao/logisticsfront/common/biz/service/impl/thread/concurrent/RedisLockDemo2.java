package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.config.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

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
//                    .addNodeAddress("192.168.1.22:6379"/*,"127.0.0.1:6380"*/)
//                    .setPassword("kdniao2017");
            config.useSentinelServers()
                    .setMasterName("logistics_01")
                    .addSentinelAddress("192.168.1.21:26379")
                    .setConnectTimeout(5000)
                    .setTimeout(5000)
                    .setPassword("kdniao2017");
            redisson = (Redisson) Redisson.create(config);
            //清空自增的ID数字
            RAtomicLong atomicLong = redisson.getAtomicLong(rAtomicName);
            atomicLong.set(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Redisson getRedisson(){
        return redisson;
    }

    /** 获取redis中的原子ID */
    public static Long nextID(){
        RAtomicLong atomicLong = getRedisson().getAtomicLong(rAtomicName);
        atomicLong.incrementAndGet();
        return atomicLong.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(redisson);
        System.out.println(null != redisson);

//        RedisLockDemo2 rd = new RedisLockDemo2();
//        long start = System.currentTimeMillis();
//
//        ExecutorService service = Executors.newFixedThreadPool(5);
//        List<Future<?>> futureTasks = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            futureTasks.add(service.submit(rd));
//            Thread.sleep(1);
//        }
//        try {
//            for (Future<?> futureTask : futureTasks) {
//                futureTask.get();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        service.shutdown();
//        long end = System.currentTimeMillis();
//
//        System.out.println((end - start) + " --- " + rd.sum);
    }

    private static boolean getLock(String lockStr) {
        boolean ifLock = false;

        if (true) {

        }

        return ifLock;
    }

    @Override
    public void run() {
        String lockStr = "lockStr007";
        System.out.println(lockStr);

        // option one
        if (false) {
            for (int i = 0; i < 5; i++) {
                try {
                    while (true) {
                        boolean ifLock = RedisLockDemo2.getLock(lockStr);
                        if (ifLock) {
                            System.out.println(Thread.currentThread().getName() + " A " + i);
                            Integer nt = sum;
                            Thread.sleep(100);
                            sum = nt + 1;
                            System.out.println(Thread.currentThread().getName() + " B " + i);
                            Thread.sleep(200);
                            System.out.println(Thread.currentThread().getName() + " C " + i + "-" + sum);

                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        System.out.println(Thread.currentThread().getName() + " release " + "\n");
//                        redisTemplate.delete(lockStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // option two
        if (true) {
            System.out.println("3---" + Thread.currentThread().getName() + " begin");
            for (int j = 0; j < 100; j++) {
                try {
                    while (true) {
                        boolean ifLock = RedisLockDemo2.getLock(lockStr);
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
//                        redisTemplate.delete(lockStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);
        }
    }
}
