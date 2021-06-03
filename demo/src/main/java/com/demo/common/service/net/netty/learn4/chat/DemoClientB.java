package com.demo.common.service.net.netty.learn4.chat;

public class DemoClientB {

    // client02
    public static void main(String[] args) {
        DemoClientHandler client = new DemoClientHandler();
        try {
            client.doExcute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}