package hello.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisTemplateConfig {
    @Value("${spring.redis.password}")
    private String password;
    
    @Value("${spring.redis.host}")
    private String host;
    
    @Value("${spring.redis.port}")
    private Integer port;
    
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    
    @Value("${spring.redis.jedis.pool.max-total}")
    private Integer maxTotal;
    
    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;
    
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;
    
    @Value("${spring.redis.jedis.pool.max-wait}")
    private Integer maxWaitMillis;
    
    private Boolean testOnBorrow = true;
    
    @Value("${spring.redis.sentinel.master:#{null}}")
    private String sentinelMaster;
    
    @Value("#{'${spring.redis.sentinel.nodes:#{null}}'.split(',')}")
    private String[] sentinelNodes;
    
    private StringRedisSerializer keySerializer = new StringRedisSerializer();
    
    private GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
    
    private StringRedisSerializer hashKeySerializer = new StringRedisSerializer();
    
    private GenericJackson2JsonRedisSerializer hashValueSerializer = new GenericJackson2JsonRedisSerializer();
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(hashKeySerializer);
        redisTemplate.setHashValueSerializer(hashValueSerializer);
        redisTemplate.afterPropertiesSet();
        
        return redisTemplate;
    }
    
    private JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = null;
        
        /* redis哨兵连接启动 */
        RedisSentinelConfiguration sentinelConfiguration = redisSentinelConfiguration();
        if (null != sentinelConfiguration) {
            jedisConnectionFactory
                    = new JedisConnectionFactory(sentinelConfiguration, jedisPoolConfig());
        }
        /* redis单例连接启动 */
        else {
            jedisConnectionFactory
                    = new JedisConnectionFactory(jedisPoolConfig());
            jedisConnectionFactory.setHostName(host);
            jedisConnectionFactory.setPort(port);
        }
        
        jedisConnectionFactory.setTimeout(timeout);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }
    
    private RedisSentinelConfiguration redisSentinelConfiguration() {
        if (StringUtils.isEmpty(sentinelMaster)) {
            return null;
        }
        
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.master(sentinelMaster);
        Set<RedisNode> redisNodeSet = new HashSet<>();
        for (String x : sentinelNodes) {
            String[] addresses = x.split(":");
            if (addresses.length == 2) {
                redisNodeSet.add(new RedisNode(addresses[0], Integer.parseInt(addresses[1])));
            } else {
                redisNodeSet.add(new RedisNode(addresses[0], 6379));
            }
        }
        redisSentinelConfiguration.setSentinels(redisNodeSet);
        
        return redisSentinelConfiguration;
    }
    
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        
        return jedisPoolConfig;
    }
}
