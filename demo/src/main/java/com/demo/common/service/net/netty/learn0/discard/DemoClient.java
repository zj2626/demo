package com.demo.common.service.net.netty.learn0.discard;

import com.demo.common.service.net.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class DemoClient extends MyNettyAddr {

    @Test
    public void client() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(2);
        excutorPool.futureGet();
    }

    /**
     *
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
                    .remoteAddress(new InetSocketAddress(serverHost, serverPort))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new DiscardHandler())
                            ;
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 异步发送");
            future.channel().closeFuture().sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!group.isShutdown()) {
                group.shutdownGracefully().sync();
            }
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 关闭");
        }

        return null;
    }

    static class DiscardHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelActive");
            ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelRead0");
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " Client received: " + in.toString(CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " exceptionCaught");
            cause.printStackTrace();
            ctx.close();
        }
    }
}
