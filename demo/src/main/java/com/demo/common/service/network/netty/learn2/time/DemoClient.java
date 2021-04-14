package com.demo.common.service.network.netty.learn2.time;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

public class DemoClient extends MyNettyAddr {

    @Test
    public void client() throws InterruptedException {
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
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new ClientHandler())
                            ;
                        }
                    });
            // 启动客户端
            ChannelFuture future = bootstrap.connect(serverHost, serverPort).sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 异步发送");

            // 等待连接关闭
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

    static class ClientHandler extends ChannelInboundHandlerAdapter {
        private ByteBuf total;

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " handlerAdded");
            total = ctx.alloc().buffer(5120);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " handlerRemoved");
            total.release();
            total = null;
        }

        /**
         * 接收服务端响应的数据
         *
         * @param ctx
         * @param msg
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelRead");

            // 收的数据都应该被累积在 total 变量里
            ByteBuf in = (ByteBuf) msg;
            System.out.println("in : " + in.readableBytes());
            total.writeBytes(in);
            in.release();
            System.out.println("total : " + total.readableBytes());

//            if (total.readableBytes() >= 1200) {
//                System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " Client received:\n "
//                        + total.toString(CharsetUtil.UTF_8));
//                ctx.close();
//            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " Client received:\n "
                    + total.toString(CharsetUtil.UTF_8));
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    static class OldClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf in = (ByteBuf) msg;
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " Client received:\n "
                    + in.toString(CharsetUtil.UTF_8));
            ctx.close();
            in.release();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
