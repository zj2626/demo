package com.demo.common.service.network.netty.source;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Map;

public class DemoClient extends MyNettyAddr {

    @Test
    public void client() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
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
    public Object doExcute(Map<String, Object> parameter) throws Exception {
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
                                    .addLast(new MyDemoClient01Handler())
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
}
