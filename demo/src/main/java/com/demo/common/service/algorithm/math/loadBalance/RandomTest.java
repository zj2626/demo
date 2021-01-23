package com.demo.common.service.algorithm.math.loadBalance;

import org.junit.Test;

import java.util.List;
import java.util.Random;

/*
负载均衡算法: 随机
 */
public class RandomTest {

    // 一般随机
    @Test
    public void test() {
        List<Server> list = Server.list();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(list.size());
            System.out.println(i + " ---> " + list.get(index));
        }
    }

    // 根据权重随机
    @Test
    public void testWithScore() {
        List<Server> list = Server.list();

        int total = list.stream().map(Server::getScore).reduce(Integer::sum).get();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(total);

            for (Server server : list) {
                if (index < server.getScore()) {
                    System.out.println(i + " ---> " + server);
                    break;
                }
                index -= server.getScore();
            }
        }
    }
}
