package com.demo.common.service.redis.callback;

import redis.clients.jedis.ShardedJedis;

public interface ConnectionCallback<T> {
    T doInRedis(ShardedJedis shardedJedis);
}
