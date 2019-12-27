package com.demo.common.service.network.netty.source;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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

    /**
     * y
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        // 第一个，通常称为“boss”，接受传入的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThread,
                new ThreadFactoryBuilder().setNameFormat("zj-bossGroup-%d").build());
        // 第二个通常称为“worker”，在boss接受连接并将接受的连接注册到worker后，它将处理接受的连接的通信量
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThread,
                new ThreadFactoryBuilder().setNameFormat("zj-workerGroup-%d").build());
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    // 使用NioServerSocketChannel类，该类用于实例化新通道以接受传入连接。 (通道channel的类型)
                    .channel(NioServerSocketChannel.class)
                    // 子通道也就是SocketChannel的处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("\n" + Thread.currentThread().getName() + " initChannel ------>");
                            socketChannel.pipeline()
                                    .addLast(new MyDemoServer01Handler())
//                                     .addLast(new MyDemoServer02Handler())
                            ;
                        }
                    })
                    // attr：设置通道的属性；
                    // 设置通道的选项参数， 对于服务端而言就是ServerSocketChannel， 客户端而言就是SocketChannel；
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 配置子通道也就是SocketChannel的选项
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
            ;
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
