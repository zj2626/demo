package com.kdniao.logisticsfront.common.biz.service.impl.redis.callback;

public interface RedisOperations {
    <T> T execute(ConnectionCallback<T> action);

    String set(final String key, final String value);

    String get(final String key);
}
