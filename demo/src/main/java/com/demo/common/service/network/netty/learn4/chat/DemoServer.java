package com.demo.common.service.network.netty.learn4.chat;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

public class DemoServer extends MyNettyAddr {

    @Test
    public void server() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThread);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast("handler", new SimpleChatServerHandler())
                            ;
                            System.out.println("SimpleChatClient:" + socketChannel.remoteAddress() + "连接上");
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ;
            ChannelFuture caChannelFuture = bootstrap.bind(serverPort).sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 启动");
            caChannelFuture.channel().closeFuture().sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully().sync();
        }

        return null;
    }

    static class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> { // (1)

        public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
            }
            channels.add(ctx.channel());
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
            }
            channels.remove(ctx.channel());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception { // (4)
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                if (channel != incoming) {
                    channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
                } else {
                    channel.writeAndFlush("[you]" + s + "\n");
                }
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "在线");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "掉线");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "异常");
            // 当出现异常就关闭连接
            cause.printStackTrace();
            ctx.close();
        }
    }
}
