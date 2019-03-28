package com.kdniao.logisticsfront.common.biz.service.impl.redis.callback;

public class RedisCallBackDemo {
    public static void main(String[] args) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.execute(shardedJedis -> {
            return shardedJedis.hget("", "");
        });

        redisTemplate.execute(new Helloback());
    }
}
