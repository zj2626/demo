package com.demo.common.service.redis.callback;

public interface RedisOperations {
    <T> T execute(ConnectionCallback<T> action);

    String set(final String key, final String value);

    String get(final String key);
}
