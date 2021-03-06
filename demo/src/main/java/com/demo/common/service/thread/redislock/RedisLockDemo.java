package com.demo.common.service.thread.redislock;

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
import java.util.UUID;
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
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
    }

    public static void main(String[] args) throws InterruptedException {
        RedisLockDemo rd = new RedisLockDemo();
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

        /*  速度对比
         * 方法一  15105    11577   11691   11585   11148   10201
         * 方法二  36474    14244   7487    6106    5979    5505
         * 方法三  8298     6744    8550    6968    6949    8149
         * */
    }

    private static boolean getLock(RedisTemplate<String, Object> redisTemplate, String lockStr, String lockValue) {
        boolean ifLock = false;

        /* 方法一 opsForValue (由于不是原子操作, 当删除不成功且在expire之前报错可能导致死锁)*/
        if (true) {
            ifLock = redisTemplate.opsForValue().setIfAbsent(lockStr, lockValue);
            if (ifLock) {
//                redisTemplate.expire(lockStr, 2500, TimeUnit.MILLISECONDS);
                redisTemplate.expire(lockStr, 3000, TimeUnit.MILLISECONDS);
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
                        valueSerializer.serialize(lockValue),
                        SafeEncoder.encode(SET_IF_NOT_EXIST),
                        SafeEncoder.encode(SET_WITH_EXPIRE_TIME), // "EX"
                        Protocol.toByteArray(3000));
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
                        valueSerializer.serialize(lockValue),
                        SafeEncoder.encode(SET_IF_NOT_EXIST),
                        SafeEncoder.encode(SET_WITH_EXPIRE_TIME), // "EX"
                        Protocol.toByteArray(3000));

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

        return ifLock;
    }

    // 当第一个线程超时 则第二个线程进入并设置redis, 然后第一个线程结束并删除redis值,会导致第三个等待的线程立即进入,此时第二个线程可能还没超时或结束
    // 所以需要设置redis的value, 且每个线程的value是不同的 可解决
    private static void releaseLock(RedisTemplate<String, Object> redisTemplate, String lockStr, String lockValue) {
        String value = String.valueOf(redisTemplate.opsForValue().get(lockValue));
        if (lockValue.equals(value)) {
            redisTemplate.delete(lockStr);
        }
    }

    @Override
    public void run() {
        String lockStr = "lockStr007";
        String lockVal = UUID.randomUUID().toString();
        System.out.println(lockStr);

        RedisTemplate<String, Object> redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");

        // option one
        if (true) {
            for (int i = 0; i < 5; i++) {
                try {
                    while (true) {
                        boolean ifLock = RedisLockDemo.getLock(redisTemplate, lockStr, lockVal);
                        if (ifLock) {
                            System.out.println(Thread.currentThread().getName() + " A " + i);
                            Integer nt = sum;
                            Thread.sleep(300);
                            sum = nt + 1;
                            System.out.println(Thread.currentThread().getName() + " B " + i);
                            Thread.sleep(300);
                            System.out.println(Thread.currentThread().getName() + " C " + i + "-------------------> " + sum);
                            for (int j = 1; j <= 4; j++) {
                                Thread.sleep(500);
                                System.out.print("*" + j + "\t");
                            }
                            System.out.println();

                            // 当线程等待超时时间设置为3000ms,则上方执行完毕以后在这里等待的10ms处发生超时,此时第二个线程马上获得锁进入
                            // 所以第二个线程的"thread-A 0"会在第一个线程的"thread-release"日志前打印出来(实际情况可能有微小误差,因为线程执行开始时间不定)
                            Thread.sleep(300);

                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        System.out.println(Thread.currentThread().getName() + " release " + "\n");
                        releaseLock(redisTemplate, lockStr, lockVal);
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
                        boolean ifLock = RedisLockDemo.getLock(redisTemplate, lockStr, lockVal);
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
                        releaseLock(redisTemplate, lockStr, lockVal);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);
        }
    }
}
