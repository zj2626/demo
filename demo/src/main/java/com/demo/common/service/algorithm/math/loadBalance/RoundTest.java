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

    /**
     * 算法轮询
     * "192.168.1.1", 6
     * "192.168.1.2", 1
     * "192.168.1.3", 3
     *
     * 循环开始: >  当前权重: [6,1,3]   权重和 = [10]
     * > *    当前权重         最大权重   最大权重ip         最大权重-权重和
     * 0.*    6,1,3           6         192.168.1.1      -4,1,3
     * 1.*    2,2,6           6         192.168.1.3      2,2,-4
     * 2.*    8,3,-1          8         192.168.1.1      -2,3,1
     * 3.*    4,4,4           4         192.168.1.1      -6,4,4
     * 4.*    0,5,7           7         192.168.1.3      0,5,-3
     * 5.*    6,6,0           6         192.168.1.1      -4,6,0
     * 6.*    2,7,3           7         192.168.1.2      2,-3,3
     * 7.*    8,-2,6          8         192.168.1.1      -2,-2,6
     * 8.*    4,-1,9          9         192.168.1.3      4,-1,-1
     * 9.*    10,0,2         10         192.168.1.1      0,0,2
     * < *    6,1,3 ...
     *
     */
    @Test
    public void testWithScore() {
        List<Server> list = Server.list();

        int total = list.stream().map(Server::getScore).reduce(Integer::sum).get();


    }
}
