package com.kdniao.logisticsfront.common.biz.service.impl.redis.callback;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisTemplate implements RedisOperations {
    private ShardedJedisPool pool;

    public void setPool(ShardedJedisPool pool) {
        this.pool = pool;
    }

    @Override
    public <T> T execute(ConnectionCallback<T> action) {
        ShardedJedis jedis = null;

        try {
            jedis = pool.getResource();
            return action.doInRedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != jedis){
                jedis.close();
            }
        }

        return null;
    }

    @Override
    public String set(String key, String value) {
        return this.execute(shardedJedis -> shardedJedis.set(key, value));
    }

    @Override
    public String get(String key) {
        return this.execute(shardedJedis -> shardedJedis.get(key));
    }
}
