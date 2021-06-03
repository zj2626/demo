package com.demo.common.service.net.netty.learn4.chat;

import com.demo.common.service.net.netty.abs.MyNettyAddr;
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
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class DemoServer extends MyNettyAddr {
    private static Logger logger = LoggerFactory.getLogger(DemoServer.class);

    @Test
    public void server() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    /**
     * https://www.w3cschool.cn/netty4userguide/ip8w1mu8.html
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public Object doExcute() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThread, new ThreadFactoryBuilder().setNameFormat("zj-bossGroup-%d").build());
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThread,
                new ThreadFactoryBuilder().setNameFormat("zj-workerGroup-%d").build());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
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
                            System.out.println("[" + LocalDateTime.now() + " " + Thread.currentThread().getName() + "] SimpleChatClient:" + socketChannel.remoteAddress() + " 上线");
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ;
            ChannelFuture caChannelFuture = bootstrap.bind(serverPort).sync();
            System.out.println("[" + LocalDateTime.now() + " " + Thread.currentThread().getName() + "] 启动");
            caChannelFuture.channel().closeFuture().sync();
            System.out.println("[" + LocalDateTime.now() + " " + Thread.currentThread().getName() + "] 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }

        return null;
    }
}
