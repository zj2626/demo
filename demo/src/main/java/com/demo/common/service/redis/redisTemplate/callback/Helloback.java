package com.demo.common.service.redis.redisTemplate.callback;

import redis.clients.jedis.ShardedJedis;

public class Helloback implements ConnectionCallback {
    @Override
    public Object doInRedis(ShardedJedis shardedJedis) {
        return "hahahahhhahaahha";
    }
}
