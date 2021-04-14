package com.demo.common.service.network.netty.learn3.pojo.test2;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.network.netty.learn3.pojo.test1.Entity;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.ReferenceCountUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

public class DemoClient extends MyNettyAddr {

    @Test
    public void client() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    /**
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public Object doExcute() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("initChannel");
                            // 添加自定义的编码器和解码器
                            // 添加POJO对象解码器 禁止缓存类加载器
                            ch.pipeline().addLast(
                                    new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this
                                            .getClass().getClassLoader())));
                            // 设置发送消息编码器
                            ch.pipeline().addLast(new ObjectEncoder());
                            // 处理网络IO
                            ch.pipeline().addLast(new ClientHandler());// 处理网络IO
                            System.out.println("initChanneled");

                        }
                    });
            // 启动客户端
            ChannelFuture future = bootstrap.connect(serverHost, serverPort).sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 异步发送");

            // 等待连接关闭
            future.channel().closeFuture().sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }

        return null;
    }

    static class ClientHandler extends ChannelInboundHandlerAdapter {

        // 客户端与服务端，连接成功的售后
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelActive");
            // 发送消息
            Entity request1 = new Entity("666");
            Entity request2 = new Entity("777");
            Entity request3 = new Entity("888");
            ctx.writeAndFlush(request1);
            ctx.writeAndFlush(request2);
            Thread.sleep(2000);
            ctx.writeAndFlush(request3);
        }

        // 只是读数据，没有写数据的话
        // 需要自己手动的释放的消息
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            try {
                Entity response = (Entity) msg;
                System.out.println(response);
            } finally {
                ReferenceCountUtil.release(msg);
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelReadComplete");
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
