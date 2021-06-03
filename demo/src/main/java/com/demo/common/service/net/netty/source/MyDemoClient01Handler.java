package com.demo.common.service.net.netty.source;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class MyDemoClient01Handler extends ChannelInboundHandlerAdapter {
    private void log(String str){
        System.out.println("\nMyDemoClient01Handler " + Thread.currentThread().getName() + " " + str);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log("channelActive");
//        super.channelActive(ctx);
        ByteBuf reqByteBuf = Unpooled.buffer(10);
        reqByteBuf.writeBytes("this is msg 信息".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(reqByteBuf);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log("channelRead");
        super.channelRead(ctx, msg);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log("userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常!!!");
        cause.printStackTrace();
        ctx.close();
    }
}
