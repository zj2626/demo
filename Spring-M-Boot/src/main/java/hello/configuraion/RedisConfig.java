package hello.configuraion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.jedis.pool.max-total}")
//    private Integer maxTotal;
//
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    private Integer maxIdle;
//
//    @Value("${spring.redis.jedis.pool.min-idle}")
//    private Integer minIdle;
//
//    @Value("${spring.redis.jedis.pool.max-wait}")
//    private Integer maxWaitMillis;
//
//    private Boolean testOnBorrow = true;
//
//    @Value("${spring.redis.sentinel.master}")
//    private String sentinelMaster;
//
//    @Value("#{'${spring.redis.sentinel.nodes}'.split(',')}")
//    private String[] sentinelNodes;

    private StringRedisSerializer keySerializer = new StringRedisSerializer();

    private GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

    private StringRedisSerializer hashKeySerializer = new StringRedisSerializer();

    private GenericJackson2JsonRedisSerializer hashValueSerializer = new GenericJackson2JsonRedisSerializer();

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(hashKeySerializer);
        redisTemplate.setHashValueSerializer(hashValueSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory
//                = new JedisConnectionFactory(redisSentinelConfiguration(), jedisPoolConfig());
//        jedisConnectionFactory.setPassword(password);
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//    }
//
//    public RedisSentinelConfiguration redisSentinelConfiguration() {
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
//        redisSentinelConfiguration.master(sentinelMaster);
//        Set<RedisNode> redisNodeSet = new HashSet<>();
//        for (String x : sentinelNodes) {
//            redisNodeSet.add(new RedisNode(x.split(":")[0], Integer.parseInt(x.split(":")[1])));
//        }
//        redisSentinelConfiguration.setSentinels(redisNodeSet);
//
//        return redisSentinelConfiguration;
//    }
//
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
//
//        return jedisPoolConfig;
//    }
}
