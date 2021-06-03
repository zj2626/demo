package com.demo.common.service.net.netty.source;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyDemoServer02Handler extends SimpleChannelInboundHandler<String> {

    private void log(String str){
        System.out.println("MyDemoServer02Handler " + Thread.currentThread().getName() + " " + str);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log("channelRead0");
        System.out.println(msg);
    }
}
