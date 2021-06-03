package com.demo.common.service.net.netty.learn5.websocketchat;

import com.demo.common.service.net.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class WebsocketChatServer extends MyNettyAddr {
    private static Logger logger = LoggerFactory.getLogger(WebsocketChatServer.class);

    @Test
    public void server() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    /**
     * https://www.w3cschool.cn/netty4userguide/sjy41mub.html
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
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpRequestHandler("/ws"));
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                            pipeline.addLast(new TextWebSocketFrameHandler());
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
