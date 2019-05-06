package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

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
 * 基于缓存（Redis等）实现分布式锁； ! ***star*** !
 * 基于Zookeeper实现分布式锁；
 */
public class RedisLockDemo implements Runnable {
    private Integer sum = 0;
    private boolean ifBlock;
    private String taskName;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static RedisTemplate<String, Object> redisTemplate;

    public RedisLockDemo(boolean ifBlock) {
        this.ifBlock = ifBlock;
    }

    static {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");
    }

    public static void main(String[] args) throws InterruptedException {
        RedisLockDemo rd = new RedisLockDemo(true);

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
    }

    @Override
    public void run() {
        String lockStr = LOCK_SUCCESS;
        System.out.println(lockStr);

        // option one
        for (int i = 0; i < 5; i++) {
            try {
                while (true) {
                    boolean ifLock = redisTemplate.opsForValue().setIfAbsent(lockStr, System.currentTimeMillis());;

                    if (ifLock) {
                        System.out.println(Thread.currentThread().getName() + " A " + i);
                        Integer nt = sum;
                        Thread.sleep(300);
                        sum = nt + 1;
                        System.out.println(Thread.currentThread().getName() + " B " + i);
                        Thread.sleep(600);
                        if (ifBlock && i == 2) {

                        }
                        System.out.println(Thread.currentThread().getName() + " C " + i + "-" + sum);

                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println(Thread.currentThread().getName() + " release " + "\n");
                    redisTemplate.delete(lockStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // option two
//        try {

//            System.out.println("2---" + Thread.currentThread().getName() + " begin");
//            for (int j = 0; j < 100000; j++) {
//                sum++;
//            }
//            System.out.println("2---" + Thread.currentThread().getName() + " done " + sum);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {

//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        // option three
//        System.out.println("3---" + Thread.currentThread().getName() + " begin");
//        for (int j = 0; j < 200; j++) {
//            try {

//                sum++;
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {

//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);
    }
}
