package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
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

        return ifLock;
    }

    @Override
    public void run() {
        String lockStr = "lockStr005";
        System.out.println(lockStr);

        RedisTemplate<String, Object> redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");

        // option one
//        for (int i = 0; i < 5; i++) {
//            try {
//                while (true) {
//                    boolean ifLock = RedisLockDemo.getLock(redisTemplate, lockStr);
//                    if (ifLock) {
//                        System.out.println(Thread.currentThread().getName() + " A " + i);
//                        Integer nt = sum;
//                        Thread.sleep(100);
//                        sum = nt + 1;
//                        System.out.println(Thread.currentThread().getName() + " B " + i);
//                        Thread.sleep(200);
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
//                    System.out.println(Thread.currentThread().getName() + " release " + "\n");
//                    redisTemplate.delete(lockStr);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // option two
        System.out.println("3---" + Thread.currentThread().getName() + " begin");
        for (int j = 0; j < 1000; j++) {
            try {
                while (true) {
                    boolean ifLock = RedisLockDemo.getLock(redisTemplate, lockStr);
                    if (ifLock) {
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

    /*public boolean setLock(RedisTemplate<String, Object> redisTemplate, String key, String value, long expire) {
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expire);
            };
            String result = redisTemplate.execute(callback);

            return !StringUtils.isEmpty(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String get(RedisTemplate<String, Object> redisTemplate, String key) {
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.get(key);
            };
            String result = redisTemplate.execute(callback);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean releaseLock(RedisTemplate<String, Object> redisTemplate, String key, String requestId) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> args = new ArrayList<>();
            args.add(requestId);

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            RedisCallback<Long> callback = (connection) -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            };
            Long result = redisTemplate.execute(callback);

            return result != null && result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 清除掉ThreadLocal中的数据，避免内存溢出
            //lockFlag.remove();
        }
        return false;
    }*/

}
