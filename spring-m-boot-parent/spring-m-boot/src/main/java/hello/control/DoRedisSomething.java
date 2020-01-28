package hello.control;

import hello.service.model.RedisRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DoRedisSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoRedisSomething.class);

    @Autowired
    private Jedis jedis;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean doredis(RedisRequest request) {
        if (null != redisTemplate) {
            try {
                redisTemplate.opsForValue().set("a", "B");
                jedis.zadd("queue_00", 1, "a");
                jedis.zadd("queue_00", 2, "b");
                jedis.zadd("queue_00", 3, "c");
                jedis.zadd("queue_00", 3, "d");
                System.out.println("---------------------------");

                System.out.println("\nexecute -> RedisCallback");
                redisTemplate.execute(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.openPipeline();
                        connection.zCount("queue_00".getBytes(), 0, 1552036878499L);
                        connection.hSet("execute-RedisCallback".getBytes(StandardCharsets.UTF_8), "aa".getBytes(StandardCharsets.UTF_8),
                                "1".getBytes(StandardCharsets.UTF_8));
                        connection.hSet("execute-RedisCallback".getBytes(StandardCharsets.UTF_8), "bb".getBytes(StandardCharsets.UTF_8),
                                "aka".getBytes(StandardCharsets.UTF_8));
                        connection.expire("execute-RedisCallback".getBytes(StandardCharsets.UTF_8), 3600);
                        List<Object> result = connection.closePipeline();
                        System.out.println("RedisCallback:  " + result);
                        return null;
                    }
                });

                System.out.println("\nexecute -> SessionCallback");
                redisTemplate.execute(new SessionCallback<String>() {
                    @Override
                    public <K, V> String execute(RedisOperations<K, V> operations) throws DataAccessException {
                        RedisOperations<String, Object> redisOperations = (RedisOperations<String, Object>) operations;
                        Long result = redisOperations.opsForZSet().count("queue_00", 0, 1552036878499L);
                        redisOperations.opsForHash().put("execute-SessionCallback", "aa", "1");
                        redisOperations.opsForHash().put("execute-SessionCallback", "bb", "aka");
                        redisOperations.expire("execute-SessionCallback", 3600, TimeUnit.SECONDS);
                        System.out.println("SessionCallback: " + result);
                        return null;
                    }
                });

                System.out.println("\nexecutePipelined -> RedisCallback");
                redisTemplate.executePipelined(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.openPipeline();
                        connection.zCount("queue_00".getBytes(), 0, 1552036878499L);
                        connection.hSet("executePipelined-RedisCallback".getBytes(StandardCharsets.UTF_8),
                                "aa".getBytes(StandardCharsets.UTF_8), "1".getBytes(StandardCharsets.UTF_8));
                        connection.hSet("executePipelined-RedisCallback".getBytes(StandardCharsets.UTF_8),
                                "bb".getBytes(StandardCharsets.UTF_8), "aka".getBytes(StandardCharsets.UTF_8));
                        connection.expire("executePipelined-RedisCallback".getBytes(StandardCharsets.UTF_8), 3600);
                        List<Object> result = connection.closePipeline();
                        System.out.println("RedisCallback:  " + result);
                        return null;
                    }
                });

                System.out.println("\nexecutePipelined -> SessionCallback");
                List<Object> sessionCallbackResult = redisTemplate.executePipelined(new SessionCallback<String>() {
                    @Override
                    public <K, V> String execute(RedisOperations<K, V> operations) throws DataAccessException {
                        RedisOperations<String, Object> redisOperations = (RedisOperations<String, Object>) operations;
                        redisOperations.opsForZSet().count("queue_00", 0, 1552036878499L);
                        redisOperations.opsForHash().put("executePipelined-SessionCallback", "aa", "1");
                        redisOperations.opsForHash().put("executePipelined-SessionCallback", "bb", "aka");
                        redisOperations.expire("executePipelined-SessionCallback", 3600, TimeUnit.SECONDS);
                        return null;
                    }
                });
                System.out.println("SessionCallback:  " + sessionCallbackResult);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }
}
