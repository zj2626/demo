package com.demo.common.service.algorithm.math.loadBalance;

import org.junit.Test;

import java.util.List;

/*
负载均衡算法: 轮询
 */
public class RoundTest {

    // 简单轮询
    @Test
    public void test() {
        List<Server> list = Server.list();

        int total = list.stream().map(Server::getScore).reduce(Integer::sum).get();

        int k = 0;
        for (int i = 0; i < 10; i++) {
            int index = k;
            for (Server server : list) {
                if (index < server.getScore()) {
                    System.out.println(i + " ---> " + server);
                    break;
                }
                index -= server.getScore();
            }

            k++;
            if(k == total){
                k = 0;
            }
        }
    }

    // 算法轮询
    @Test
    public void testWithScore() {
        List<Server> list = Server.list();

        int total = list.stream().map(Server::getScore).reduce(Integer::sum).get();


    }
}
