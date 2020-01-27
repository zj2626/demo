package com.demo.common.service.lru;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

public class Demo {

    /**
     * 模拟LRU
     */
    @Test
    public void main() {
        int cacheSize = 8;
        System.out.println("最大数量: " + ((int) Math.ceil(cacheSize / 0.75) + 1));
        System.out.println("可缓存数量: " + (cacheSize));
        LRUCache lruCache = new LRUCache(cacheSize);

        // 当存储数据量大于[可缓存数量]时, 会自动删除最老的数据(其中key=obj14的数据由于一直在访问,所以不回被删除)
        for (int i = 10; i < 30; i++) {
            lruCache.put("obj" + i, "*");
            System.out.println(lruCache.size() + " - " + JSON.toJSONString(lruCache.keySet()) + "\t | 查询key: " + lruCache.get("obj14"));
        }
    }
}
