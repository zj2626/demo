package com.demo.common.service.network.netty.test01;

import com.alibaba.fastjson.JSON;
import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Demo01 extends MyNettyAddr {

    @Test
    public void client() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new MyTimeClientHandler())
                            ;
                        }
                    });
            ChannelFuture future = bootstrap.connect(serverHost, serverPort).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " Listener");
                    System.out.println(channelFuture.isDone());
                    System.out.println(channelFuture.isSuccess());
                    System.out.println(channelFuture.isCancelled());
                }
            });
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 异步发送");
            future.channel().closeFuture().sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!group.isShutdown()) {
                group.shutdownGracefully();
            }
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 关闭");
        }

        return null;
    }

    static class MyTimeClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelActive");

            Map<String, Object> data = new HashMap<>();
            data.put("source", UUID.randomUUID().toString());
            data.put("clientName", Thread.currentThread().getName());
            data.put("date", LocalDateTime.now());
            data.put("memo", "测试中文" + new Random().nextInt(100));

            byte[] reqMsgByte = JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8);
            ByteBuf reqByteBuf = Unpooled.buffer(reqMsgByte.length);
            reqByteBuf.writeBytes(reqMsgByte);
            ctx.writeAndFlush(reqByteBuf);
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelActive done");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelRead");
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " exceptionCaught");
            cause.printStackTrace();
            ctx.close();
        }
    }
}
