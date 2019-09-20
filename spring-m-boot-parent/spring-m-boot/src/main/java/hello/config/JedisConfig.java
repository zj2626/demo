package hello.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class JedisConfig {
    @Value("${spring.redis.password}")
    private String password;
    
    @Value("${spring.redis.host:#{null}}")
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
    
    @Bean
    public Jedis jedis() {
        Pool<Jedis> jedisPool = jedisSentinel();
        
        if (null == jedisPool) {
            jedisPool = new JedisPool(
                    jedisPoolConfig(),
                    this.host,
                    this.port,
                    this.timeout,
                    this.password,
                    0,
                    null
            );
        }
        
        return jedisPool.getResource();
        
    }
    
    private JedisSentinelPool jedisSentinel() {
        if (StringUtils.isEmpty(sentinelMaster)) {
            return null;
        }
        
        Set<String> redisNodeSet = new HashSet<>();
        for (String x : sentinelNodes) {
            redisNodeSet.add(x);
        }
        return new JedisSentinelPool(sentinelMaster, redisNodeSet, jedisPoolConfig());
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
