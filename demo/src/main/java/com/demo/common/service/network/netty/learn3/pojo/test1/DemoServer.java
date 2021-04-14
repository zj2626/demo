package com.demo.common.service.network.netty.learn3.pojo.test1;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

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
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new ServerToByteEncoderHandler(), new MyServerHandler())
                            ;
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
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

    // 时间服务器 (忽略任何接收到的数据)
    static class MyServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelActive");

            String out =
                    ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a1\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a2\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a2\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a3\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a3\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a4\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a4\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a5\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a5\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a6\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a6\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a7\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a7\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a8\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a8\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a9\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a9\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a0\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a0\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a1\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a1\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a2\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a2\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a3\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a3\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a4\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a4\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a5\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a5\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a6\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a6\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a7\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a7\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a8\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a8\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a9\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a9\n" +
                            ">赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞-赞a000\n";
            Entity entity = new Entity(out);
            System.out.println(Thread.currentThread().getName());

            ChannelFuture f = ctx.writeAndFlush(entity);
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    static class ServerEncoderHandler extends ChannelOutboundHandlerAdapter {
        /**
         * 编码
         *
         * @param ctx
         * @param msg
         * @param promise
         */
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " write");
            Entity entity = (Entity) msg;
            ByteBuf data = ctx.alloc().buffer(10240);
            data.writeBytes(entity.getName().getBytes());
            ctx.write(data, promise);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    static class ServerToByteEncoderHandler extends MessageToByteEncoder<Entity> {
        @Override
        protected void encode(ChannelHandlerContext ctx, Entity msg, ByteBuf out) {
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " encode");
            out.writeBytes(msg.getName().getBytes());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
