package com.kdniao.logisticsfront.common.biz.service.impl.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RedisTest {

    public static void main(String args[]) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        RedisUtil redisUtil = (RedisUtil) applicationContext.getBean("redisUtil");
//        redisUtil.flushDB();
//
//        redisUtil.set("index0", "2333");
//        redisUtil.set("index2", 2333);
//        System.out.println("index>>>>" + redisUtil.get("index0") + "----" + redisUtil.get("index1"));
//
//        User user1 = new User("zhangj", 20, "深圳市2", 233.100F, 999.999);
//        User user2 = new User("zhang_test", 18, "what fuck", 123F, 999.00);
//        redisUtil.hset("user", user1.getName(), JSON.toJSON(user1));
//        redisUtil.hset("user", user2.getName(), JSON.toJSON(user2));
//        redisUtil.hset("user", "test key", "test value");
//        System.out.println("user     >>>>" + redisUtil.hget("user", user1.getName()));
//        System.out.println("user     >>>>" + redisUtil.hget("user", user2.getName()));
//        Map<Object, Object> map = redisUtil.hmget("user");
//        System.out.println("user list>>>>" + redisUtil.hmget("user"));
//        System.out.println("#################################");

//        testMore(applicationContext);
        testMore2(applicationContext);
    }

    private static void testMore(ApplicationContext applicationContext) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);

        RedisTemplate redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");

        long n = 801100000020L;
        for (int i = 0; i < 999; i++) {
            redisTemplate.opsForList().rightPush("MailNo_ZJS_0_ZJS", n + i + "");
        }

        System.out.println("#########");
        System.out.println(System.currentTimeMillis());
        redisTemplate.opsForZSet().add("kdfafa-subscribe-route-pull-set", "ANE|4565121212112", System.currentTimeMillis() + 30000);
        Set obj = redisTemplate.opsForZSet().rangeByScore("kdfafa-subscribe-route-pull-set", 0, System.currentTimeMillis(), 0, 10);
        System.out.println(obj.size());
        System.out.println(obj);
        System.out.println("#########");

//        redisTemplate.opsForValue().set("name", "tom23");
//        redisTemplate.opsForValue().multiSet(new HashMap(){{
//            put("a", "bbbb");
//            put("c", "dddd");
//        }});

//        Set zSetValue = redisTemplate.opsForZSet().range("zkey", 1, 8);
//        System.out.println("zSetValue>>>>" + zSetValue);
//        zSetValue = redisTemplate.opsForZSet().rangeByScore("zkey", 1, 8);
//        System.out.println("zSetValue>>>>" + zSetValue);
//        zSetValue = redisTemplate.opsForZSet().rangeByScore("zkey", 1, 8, 1, 3);
//        System.out.println("zSetValue>>>>" + zSetValue);

//        long now = new Date().getTime();
//        String data = nf.format(redisTemplate.opsForZSet().score("route_pulling_queue_01", "\"DEMO-201901142877\""));
//        System.out.println(now);
//        System.out.println(data);
//        System.out.println(now > Long.valueOf(data));
//        Set zSetValue = redisTemplate.opsForZSet().range("route_pulling_queue_01", 0, 1);
//        System.out.println("zSetValue>>>>" + zSetValue);
//        zSetValue = redisTemplate.opsForZSet().rangeByScore("route_pulling_queue_01", 0, new Date().getTime());
//        System.out.println("zSetValue>>>>" + zSetValue);
//        zSetValue = redisTemplate.opsForZSet().rangeByScore("route_pulling_queue_01", 0, new Date().getTime(), 0, 2000);
//        System.out.println("zSetValue>>>>" + zSetValue);

//        System.out.println("string>>>>" + redisTemplate.opsForValue().get("name"));
//        redisTemplate.opsForValue().set("name", "beeefff");
//        System.out.println("string>>>>" + redisTemplate.opsForValue().get("name"));
//        redisTemplate.opsForValue().setIfAbsent("name", "agggggg");
//        System.out.println("string>>>>" + redisTemplate.opsForValue().get("name"));
//        redisTemplate.opsForValue().set("sex", "tom");
//        System.out.println("string>>>>" + redisTemplate.opsForValue().get("sex"));
////        redisTemplate.opsForValue().increment("sex", Long.parseLong("2"));
//        System.out.println("string>>>>" + redisTemplate.opsForValue().get("sex"));
//
//        redisTemplate.opsForHash().put("hash key", "h1", "sex1");
//        redisTemplate.opsForHash().put("hash key", "h2", "sex2");
//        redisTemplate.opsForHash().put("hash2 key", "h3", "sex3");
//        redisTemplate.opsForHash().put("hash2 key", "h4", "sex4");
//        System.out.println("hash>>>>" + redisTemplate.opsForHash().get("hash key", "h1"));
//        System.out.println("hash>>>>" + redisTemplate.opsForHash().entries("hash key"));
//        System.out.println("hash>>>>" + redisTemplate.opsForHash().keys("hash key"));
//        System.out.println("hash>>>>" + redisTemplate.opsForHash().values("hash key"));
//        redisTemplate.opsForHash().delete("hash key", "h1");
//
//        redisTemplate.opsForList().leftPush("list", "a");
//        redisTemplate.opsForList().leftPush("list", "b");
//        redisTemplate.opsForList().leftPush("list", "c");
//        redisTemplate.opsForList().leftPush("list", "d");
//        redisTemplate.opsForList().rightPush("list", "c");
//        redisTemplate.opsForList().rightPush("list", "e");
//        redisTemplate.opsForList().rightPush("list", "f");
//        redisTemplate.opsForList().rightPush("list", "g");
//        System.out.println("list>>>>" + redisTemplate.opsForList().range("list", 0, -1));
//        redisTemplate.opsForList().rightPop("list");
//        redisTemplate.opsForList().rightPop("list");
//        System.out.println("list>>>>" + redisTemplate.opsForList().range("list", 0, -1));
//        redisTemplate.opsForList().remove("list", 4, "c");
//        System.out.println("list>>>>" + redisTemplate.opsForList().range("list", 0, -1));
//
//        redisTemplate.opsForSet().add("sets", "1", "2", "3", "4", "4", "5");
//        redisTemplate.opsForSet().add("set2", "z", "y", "w", "v", "e", "c");
//        System.out.println("sets>>>>" + redisTemplate.opsForSet().members("sets"));
//        redisTemplate.opsForSet().add("sets", "6", '7');
//        System.out.println("sets>>>>" + redisTemplate.opsForSet().members("sets"));
//        redisTemplate.opsForSet().pop("sets");
//        System.out.println("sets>>>>" + redisTemplate.opsForSet().members("sets"));
//        redisTemplate.opsForSet().remove("sets", "c");
//        System.out.println("sets>>>>" + redisTemplate.opsForSet().members("sets"));
//        System.out.println("difference>>>>>>>>>>>>>>>>" + redisTemplate.opsForSet().difference("sets", "set2"));
//        System.out.println("intersect>>>>>>>>>>>>>>>>" + redisTemplate.opsForSet().intersect("sets", "set2"));
//        System.out.println("union>>>>>>>>>>>>>>>>" + redisTemplate.opsForSet().union("sets", "set2"));
//        System.out.println("isMember>>>>>>>>>>>>>>>>" + redisTemplate.opsForSet().isMember("sets", "6"));
//
//        redisTemplate.opsForZSet().add("zSets", "a", -1);
//        redisTemplate.opsForZSet().add("zSets", "b", 10);
//        redisTemplate.opsForZSet().add("zSets", "c", 11);
//        redisTemplate.opsForZSet().add("zSets", "d", 12);
//        redisTemplate.opsForZSet().add("zSets", "e", 14);
//        redisTemplate.opsForZSet().add("zSets", "f", 16);
//        System.out.println("sorted sets>>>>" + redisTemplate.opsForZSet().range("zSets", 0, -1));
//        redisTemplate.opsForZSet().remove("zSets", "e");
//        System.out.println("sorted sets>>>>" + redisTemplate.opsForZSet().range("zSets", 0, -1));
//        redisTemplate.opsForZSet().removeRange("zSets", 2, 3);
//        System.out.println("sorted sets>>>>" + redisTemplate.opsForZSet().range("zSets", 0, -1));
//
//        redisTemplate.expire("zSets", 10, TimeUnit.SECONDS);
//
//        System.out.println(redisTemplate.boundValueOps("name").get());
//        System.out.println(redisTemplate.boundHashOps("hash2 key").values());
    }

    private static void testMore2(ApplicationContext applicationContext) {
        RedisTemplate redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");

        List<String> zSet = Arrays.asList(
                "route_pulling_queue_00",
                "route_pulling_queue_05",
                "route_sub_followup_queue_00"
        );

        List<String> hash = Arrays.asList(
                "route_subscription_01",
                "route_subscription_04"
        );

        List result = null;

        /**
         * execute RedisCallback
         * */
//        redisTemplate.execute(new RedisCallback() {
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.openPipeline();
//
//                // dataType == null -> true
//                zSet.forEach(s -> System.out.println((null == connection.type(s.getBytes()))));
//
//                zSet.forEach(s -> connection.zCount(s.getBytes(), 0, 100000));
//
//                zSet.forEach(s -> connection.zCard(s.getBytes()));
//
//                hash.forEach(s -> connection.hLen(s.getBytes()));
//
//                List<Object> result = connection.closePipeline();
//                System.out.println("result>>>>>>>>>>");
//                System.out.println(result);
//
//                return null;
//
//            }
//        });

        /**
         * executePipelined RedisCallback
         * */
//        result = redisTemplate.executePipelined(new RedisCallback<List<String>>() {
//            @Override
//            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
//
//                zSet.forEach(s -> connection.zCard(s.getBytes()));
//
//                hash.forEach(s -> connection.hLen(s.getBytes()));
//
//                return null;
//            }
//        });
//
//        System.out.println("result>>>>>>>>>>");
//        System.out.println(result);

        /**
         * execute SessionCallback
         * */
//        redisTemplate.execute(new SessionCallback<Object>() {
//            @Override
//            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
//                RedisOperations<String, Object> redisOperations = (RedisOperations<String, Object>) operations;
//                zSet.forEach(s -> redisOperations.opsForZSet().zCard(s));
//
//                hash.forEach(s -> redisOperations.opsForHash().size(s));
//
//                return null;
//            }
//        });
//        System.out.println("result>>>>>>>>>>");
//
//        result = redisTemplate.executePipelined(new SessionCallback<Object>() {
//            @Override
//            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
//                RedisOperations<String, Object> redisOperations = (RedisOperations<String, Object>) operations;
//                zSet.forEach(s -> redisOperations.opsForZSet().zCard(s));
//
//                hash.forEach(s -> redisOperations.opsForHash().size(s));
//
//                return null;
//            }
//        });
//
//        System.out.println(result);
//        System.out.println("result>>>>>>>>>>");

        /*test transaction*/
//        try {
//            redisTemplate.execute(new SessionCallback<Object>() {
//                @Override
//                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
//                    RedisOperations<String, Object> redisOperations = (RedisOperations<String, Object>) operations;
//                    redisOperations.opsForValue().set("aaaa", "a");
//                    redisOperations.opsForValue().set("aaaa", "b");
//
//                    System.out.println(2 / 0);
//
//                    redisOperations.opsForValue().set("aaaa", "c");
//
//                    return null;
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /*test transaction*/
        try {
            redisTemplate.executePipelined(new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    RedisOperations<String, Object> redisOperations = (RedisOperations<String, Object>) operations;
                    redisOperations.opsForValue().set("bbb", "a");
                    redisOperations.opsForValue().set("bbb", "b");

                    if(true){
                        throw new RuntimeException();
                    }

                    redisOperations.opsForValue().set("bbb", "c");

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        result = redisTemplate.executePipelined(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set("bbb".getBytes(), "b".getBytes());

                if(true){
                    throw new RuntimeException();
                }

                connection.set("bbb".getBytes(), "c".getBytes());

                return null;
            }
        });
    }
}
