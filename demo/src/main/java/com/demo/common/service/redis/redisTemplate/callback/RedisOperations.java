package com.demo.common.service.redis.redisTemplate.callback;

public interface RedisOperations {
    <T> T execute(ConnectionCallback<T> action);

    String set(final String key, final String value);

    String get(final String key);
}
