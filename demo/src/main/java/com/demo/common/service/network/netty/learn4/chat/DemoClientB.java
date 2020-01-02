package com.demo.common.service.network.netty.learn4.chat;

public class DemoClientB {

    // client02
    public static void main(String[] args) {
        DemoClientHandler client = new DemoClientHandler();
        try {
            client.doExcute(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}