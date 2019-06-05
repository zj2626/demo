package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

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
 * 基于数据库实现分布式锁；
 * 基于缓存（Redis等）实现分布式锁； ! ***star*** !
 * 基于Zookeeper实现分布式锁；
 */
public class RedisLockDemo implements Runnable {
    private Integer sum = 0;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String OPTION = "set";

    private static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    public static void main(String[] args) throws InterruptedException {
        RedisLockDemo rd = new RedisLockDemo();
        long start = System.currentTimeMillis();

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
        long end = System.currentTimeMillis();

        System.out.println((end - start) + " --- " + rd.sum);

        /*
         * 方法一  15105    11577   11691   11585   11148   10201
         * 方法二  36474    14244   7487    6106    5979    5505
         * 方法三  8298     6744    8550    6968    6949    8149
         * 方法四  13132    10512   10944
         * */
    }

    private static boolean getLock(RedisTemplate<String, Object> redisTemplate, String lockStr) {
        boolean ifLock = false;

        /* 方法一 opsForValue (由于不是原子操作, 当删除不成功且在expire之前报错可能导致死锁)*/
        if (false) {
            ifLock = redisTemplate.opsForValue().setIfAbsent(lockStr, String.valueOf(System.currentTimeMillis()));
            if (ifLock) {
                redisTemplate.expire(lockStr, 2000, TimeUnit.MILLISECONDS);
            }
        }

        /* 方法二  execute*/
        if (false) {
            RedisCallback<String> callback = connection -> {
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                Object object = connection.execute(
                        OPTION,
                        keySerializer.serialize(lockStr),
                        valueSerializer.serialize(String.valueOf(System.currentTimeMillis())),
                        SafeEncoder.encode(SET_IF_NOT_EXIST),
                        SafeEncoder.encode(SET_WITH_EXPIRE_TIME), // "EX"
                        Protocol.toByteArray(2000));
                return null != object ? new String((byte[]) object) : null;
            };
            String ifLockStr = redisTemplate.execute(callback);
            if (null != ifLockStr) {
                ifLock = LOCK_SUCCESS.equalsIgnoreCase(ifLockStr);
            }
        }

        /* 方法三 executePipelined*/
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

        /* 方法四 */
        if (true) {
            ifLock = lock(redisTemplate, lockStr, String.valueOf(System.currentTimeMillis()));
        }

        return ifLock;
    }

    public static boolean lock(RedisTemplate<String, Object> redisTemplate, String key, String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {//setNX 返回boolean
            return true;
        }
        //如果锁超时 ***
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldvalue = (String) redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldvalue) && oldvalue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        String lockStr = "lockStr007";
        System.out.println(lockStr);

        RedisTemplate<String, Object> redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");

        // option one
        if (false) {
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
        }

        // option two
        if (true) {
            System.out.println("3---" + Thread.currentThread().getName() + " begin");
            for (int j = 0; j < 100; j++) {
                try {
                    while (true) {
                        boolean ifLock = RedisLockDemo.getLock(redisTemplate, lockStr);
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
                        redisTemplate.delete(lockStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);
        }
    }
}
