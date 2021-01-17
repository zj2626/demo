package com.demo.common.service.algorithm.math.loadBalance;

import com.demo.common.service.algorithm.math.loadBalance.bo.WeightBo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
负载均衡算法: 轮询
 */
public class RoundTest {

    private int current = 0;
    private static List<Server> orgin = Server.list();
    private static List<WeightBo> list;

    static {
        list = orgin.stream().map(o -> {
            WeightBo weightBo = new WeightBo();
            weightBo.setIp(o.getIp());
            weightBo.setScore(o.getScore());
            weightBo.setOriginWeight(o.getScore());
            weightBo.setCurrentWeight(o.getScore());
            return weightBo;
        }).collect(Collectors.toList());
    }

    // 简单轮询
    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            getServer();
        }
    }

    private void getServer() {
        int total = orgin.stream().map(Server::getScore).reduce(Integer::sum).get();

        int index = current;
        for (Server server : orgin) {
            if (index < server.getScore()) {
                System.out.println(" ---> " + server);
                break;
            }
            index -= server.getScore();
        }

        current++;
        if (current == total) {
            current = 0;
        }
    }

    /**
     * 算法轮询
     * "192.168.1.1", 6
     * "192.168.1.2", 1
     * "192.168.1.3", 3
     * <p>
     * 循环开始: >  当前权重: [6,1,3]   权重和 = [10]
     * > *    当前权重         最大权重   最大权重ip         最大权重-权重和
     * 0.*    [6, 1, 3]       6         192.168.1.1      [-4, 1, 3]
     * 1.*    [2, 2, 6]       6         192.168.1.3      [2, 2, -4]
     * 2.*    [8, 3, -1]      8         192.168.1.1      [-2, 3, -1]
     * 3.*    [4, 4, 2]       4         192.168.1.1      [-6, 4, 2]
     * 4.*    [0, 5, 5]       5         192.168.1.2      [0, -5, 5]
     * 5.*    [6, -4, 8]      8         192.168.1.3      [6, -4, -2]
     * 6.*    [12, -3, 1]     12        192.168.1.1      [2, -3, 1]
     * 7.*    [8, -2, 4]      8         192.168.1.1      [-2, -2, 4]
     * 8.*    [4, -1, 7]      7         192.168.1.3      [4, -1, -3]
     * 9.*    [10, 0, 0]     10         192.168.1.1      [0, 0, 0]
     * < *    [6, 1, 3]  ... ...
     */
    @Test
    public void testWithScore() {
        for (int i = 0; i < 15; i++) {
            getServerWithScore();
        }
    }

    private void getServerWithScore() {
        int total = list.stream().map(Server::getScore).reduce(Integer::sum).get();

        WeightBo server = null;
        for (WeightBo bo : list) {
            if(null == server || server.getCurrentWeight() < bo.getCurrentWeight()){
                server = bo;
            }
        }
        System.out.println(" ---> " + server.getIp());

        // 设置下次权重
        server.setCurrentWeight(server.getCurrentWeight() - total);
        for (WeightBo bo : list) {
            bo.setCurrentWeight(bo.getCurrentWeight() + bo.getOriginWeight());
        }
    }
}
