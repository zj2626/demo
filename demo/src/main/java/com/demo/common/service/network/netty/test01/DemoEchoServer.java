package com.demo.common.service.network.netty.test01;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Map;

public class DemoEchoServer extends MyNettyAddr {

    @Test
    public void server() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    /**
     * https://www.w3cschool.cn/essential_netty_in_action/essential_netty_in_action-ofey289e.html
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public Object doExcute() throws Exception {
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
                                    .addLast(new MyEchoServerHandler())
                            ;
                        }
                    });
            ChannelFuture caChannelFuture = bootstrap.bind(serverPort).sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 阻塞");
            caChannelFuture.channel().closeFuture().sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!bossGroup.isShutdown()) {
                bossGroup.shutdownGracefully().sync();
            }
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 关闭");
        }

        return null;
    }

    /**
     * @Sharable 标识这类的实例之间可以在 channel 里面共享
     * <p>
     * channelRead() - 每个信息入站都会调用
     * channelReadComplete() - 通知处理器最后的 channelread() 是当前批处理中的最后一条消息时调用
     * exceptionCaught()- 读操作时捕获到异常时调用
     */
//    @ChannelHandler.Sharable
    static class MyEchoServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelRead");
            ByteBuf in = (ByteBuf) msg;
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " Server received: " + in.toString(CharsetUtil.UTF_8));
            ctx.write(in);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelReadComplete");
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " exceptionCaught");
            cause.printStackTrace();
            ctx.close();
        }
    }
}
