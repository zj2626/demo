package com.demo.common.service.algorithm.math.loadBalance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    private String ip;
    private int score;

    public static List<Server> list(){
        List<Server> list = new ArrayList<>();
        list.add(new Server("192.168.1.1", 6));
        list.add(new Server("192.168.1.2", 1));
        list.add(new Server("192.168.1.3", 3));
        return list;
    }
}
