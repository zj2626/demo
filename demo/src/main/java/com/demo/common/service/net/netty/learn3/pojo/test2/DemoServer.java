package com.demo.common.service.net.netty.learn3.pojo.test2;

import com.demo.common.service.net.netty.abs.MyNettyAddr;
import com.demo.common.service.net.netty.learn3.pojo.test1.Entity;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

import java.time.LocalDateTime;

public class DemoServer extends MyNettyAddr {

    @Test
    public void server() throws InterruptedException {
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
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThread);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThread);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("initChannel");
                            // 添加对象解码器 负责对序列化POJO对象进行解码 设置对象序列化最大长度为10K 防止内存溢出
                            // 设置线程安全的WeakReferenceMap对类加载器进行缓存 支持多线程并发访问 防止内存溢出
                            ch.pipeline().addLast(
                                    new ObjectDecoder(10 * 1024, ClassResolvers
                                            .weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            // 添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
                            ch.pipeline().addLast(new ObjectEncoder());
                            // 处理网络IO
                            ch.pipeline().addLast(new ServerHandler());
                            System.out.println("initChanneled");
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ;
            ChannelFuture caChannelFuture = bootstrap.bind(serverPort).sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 阻塞");
            caChannelFuture.channel().closeFuture().sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }

        return null;
    }

    static class ServerHandler extends ChannelInboundHandlerAdapter {
        // 用于获取客户端发送的信息
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 用于获取客户端发来的数据信息
            Entity body = (Entity) msg;
            System.out.println("Server接受的客户端的信息 :" + body.toString());

            // 会写数据给客户端
            body.setSex(2);
            // 当服务端完成写操作后，关闭与客户端的连接
            ctx.writeAndFlush(body);
            // .addListener(ChannelFutureListener.CLOSE);

            // 当有写操作时，不需要手动释放msg的引用
            // 当只有读操作时，才需要手动释放msg的引用
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
