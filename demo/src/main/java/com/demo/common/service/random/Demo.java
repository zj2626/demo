package com.demo.common.service.random;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Demo {
    static {
        System.out.println(System.currentTimeMillis());
    }

    /**
     * Random随机数并不随机 相同的种子生成的随机数是一样的;生成的值可以预测
     * Random线程安全,多线程下速度慢
     */
    @Test
    public void random() {
        // 参数: 种子
        Random random = new Random(1574690219326L);
        System.out.println(random.nextInt(5));
        random = new Random(1574690219326L);
        System.out.println(random.nextInt(5));
        random = new Random(1574690219326L);
        System.out.println(random.nextInt(5));
    }

    /**
     * ThreadLocalRandom比Random快,和线程绑定
     */
    @Test
    public void threadLocalRandom() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        System.out.println(random.nextInt(5));
        random = ThreadLocalRandom.current();
        System.out.println(random.nextInt(5));
        random = ThreadLocalRandom.current();
        System.out.println(random.nextInt(5));
    }

    /**
     * 使用内置两种随机数算法，NativePRNG(默认)和SHA1PRNG [ jvm 启动参数可配: -Djava.security=file:/dev/urandom]
     * 首次调用性能比较差
     * SHA1PRNG的性能将近要比NativePRNG的性能好
     */
    @Test
    public void secureRandom() {
        SecureRandom random = new SecureRandom();
        System.out.println(random.nextInt(5));

        try {
            // 参数: 算法名
            random = SecureRandom.getInstance("SHA1PRNG");
            System.out.println(random.nextInt(5));

            // 参数: 算法名, 算法程序包
            random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            System.out.println(random.nextInt(5));
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }
}
