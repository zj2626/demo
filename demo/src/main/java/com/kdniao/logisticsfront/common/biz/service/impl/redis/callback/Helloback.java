package com.kdniao.logisticsfront.common.biz.service.impl.redis.callback;

import redis.clients.jedis.ShardedJedis;

public class Helloback implements ConnectionCallback {
    @Override
    public Object doInRedis(ShardedJedis shardedJedis) {
        return "hahahahhhahaahha";
    }
}