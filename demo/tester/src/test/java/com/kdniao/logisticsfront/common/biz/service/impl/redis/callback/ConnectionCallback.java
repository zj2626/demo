package com.kdniao.logisticsfront.common.biz.service.impl.redis.callback;

import redis.clients.jedis.ShardedJedis;

public interface ConnectionCallback<T> {
    T doInRedis(ShardedJedis shardedJedis);
}
