package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;
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
    private static final String OPTION = "set";
    public static final String UNLOCK_LUA = "test";

    private static ApplicationContext applicationContext;

    public RedisLockDemo(boolean ifBlock) {
        this.ifBlock = ifBlock;
    }

    static {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
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

    private static boolean getLock(RedisTemplate<String, Object> redisTemplate, String lockStr) {
        boolean ifLock = false;

        /* 方法一 (由于不是原子操作, 当删除不成功且在expire之前报错可能导致死锁)*/
        if (true) {
            ifLock = redisTemplate.opsForValue().setIfAbsent(lockStr, String.valueOf(System.currentTimeMillis()));
            if (ifLock) {
                redisTemplate.expire(lockStr, 2000, TimeUnit.MILLISECONDS);
            }
        }

        /* 方法二 */
        if (false) {
            RedisCallback<String> callback = connection -> {
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                connection.execute(
                        OPTION,
                        keySerializer.serialize(lockStr),
                        valueSerializer.serialize(String.valueOf(System.currentTimeMillis())),
                        SafeEncoder.encode(SET_IF_NOT_EXIST),
                        SafeEncoder.encode(SET_WITH_EXPIRE_TIME), // "EX"
                        Protocol.toByteArray(2000));

                return null;
            };
            List<Object> ifLocks = redisTemplate.executePipelined(callback);
            if (!CollectionUtils.isEmpty(ifLocks)) {
                Object object = ifLocks.get(0);
                if (null != object) {
                    ifLock = LOCK_SUCCESS.equalsIgnoreCase(ifLocks.get(0).toString());
                }
            }
        }

        /* 方法三  */
        if (false) {
            RedisCallback<Object> callback = connection -> {
                RedisConnection redisConnection = connection;

                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                Object object = redisConnection.execute(
                        OPTION,
                        keySerializer.serialize(lockStr),
                        valueSerializer.serialize(String.valueOf(System.currentTimeMillis())),
                        SafeEncoder.encode(SET_IF_NOT_EXIST),
                        SafeEncoder.encode(SET_WITH_EXPIRE_TIME), // "EX"
                        Protocol.toByteArray(2000));
                return object;
            };
            Object ifLockStr = redisTemplate.execute(callback);
            if (null != ifLockStr) {
                ifLock = true;
            }
        }

        return ifLock;
    }

    @Override
    public void run() {
        String lockStr = "lockStr007";
        System.out.println(lockStr);

        RedisTemplate<String, Object> redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");

        // option one
        for (int i = 0; i < 5; i++) {
            try {
                while (true) {
                    boolean ifLock = RedisLockDemo.getLock(redisTemplate, lockStr);
                    if (ifLock) {
                        System.out.println(Thread.currentThread().getName() + " A " + i);
                        Integer nt = sum;
                        Thread.sleep(100);
                        sum = nt + 1;
                        System.out.println(Thread.currentThread().getName() + " B " + i);
                        Thread.sleep(200);
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
//        System.out.println("3---" + Thread.currentThread().getName() + " begin");
//        for (int j = 0; j < 200; j++) {
//            try {
//                while (true) {
//                    boolean ifLock = RedisLockDemo.getLock(redisTemplate, lockStr);
//                    if (ifLock) {
//                        System.out.println(Thread.currentThread().getName() + " - " + sum);
//                        sum++;
//
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    redisTemplate.delete(lockStr);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);
    }
}
