package com.demo.common.service.network.netty.source;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Map;

public class DemoServer extends MyNettyAddr {

    @Test
    public void client() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    /**
     *y
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThread);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(serverPort))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new MyDemoServer01Handler())
//                                     .addLast(new MyDemoServer02Handler())
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
}
