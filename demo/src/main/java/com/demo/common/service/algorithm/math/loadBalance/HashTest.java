package com.demo.common.service.algorithm.math.loadBalance;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/*
负载均衡算法: 哈希
 */
public class HashTest {

    private static Integer v_node = 10;
    private static List<Server> orgin = Server.list();
    private static SortedMap<Integer, String> nodeMap = new TreeMap<>();

    static {
        for (int i = 0; i < v_node; i++) {
            for (Server server : orgin) {
                int hash = FNVHash1(server.getIp() + " = " + i);
                nodeMap.put(hash, server.getIp());
            }
        }
    }

    @Test
    public void testTree() {
        // 红黑树 了解
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        sortedMap.put("1", "eee");
        sortedMap.put("2", "ddd");
        sortedMap.put("3", "cc");
        sortedMap.put("4", "AA");
        sortedMap.put("5", "BBbb");
        sortedMap.put("6", "mm");
        sortedMap.put("7", "ZzZz");

        // 寻找大于等于"4"的子树
        SortedMap<String, Object> tailMap = sortedMap.tailMap("4");
        System.out.println(JSON.toJSON(tailMap));

        // 寻找大于等于"4"的子树中 第一个节点
        String key = tailMap.firstKey();
        System.out.println(key);

    }

    @Test
    public void test() {
        System.out.println(JSON.toJSON(nodeMap));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                getServer("clientIp" + j);
            }
            System.out.println("======================");
        }
    }

    private void getServer(String clientIp) {
        int hash = FNVHash1(clientIp);
        SortedMap<Integer, String> tailMap = nodeMap.tailMap(hash);

        if (tailMap.isEmpty()) {
            System.out.println(nodeMap.firstKey() + " ---> " + nodeMap.get(nodeMap.firstKey()));
        } else {
            System.out.println(tailMap.firstKey() + " ---> " + tailMap.get(tailMap.firstKey()));
        }
    }

    public static int FNVHash1(String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < data.length(); i++)
            hash = (hash ^ data.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }
}
