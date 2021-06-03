package com.demo.common.service.redis.redisTemplate.callback;

import redis.clients.jedis.ShardedJedis;

public interface ConnectionCallback<T> {
    T doInRedis(ShardedJedis shardedJedis);
}
