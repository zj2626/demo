package com.demo.common.service.net.netty.learn2.time;

import com.demo.common.service.net.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class DemoClient2 extends MyNettyAddr {

    @Test
    public void client() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    /**
     * https://www.w3cschool.cn/netty4userguide/b3ia1mtl.html
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
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new ClientDecoderHandler(), new ClientHandler())
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
            group.shutdownGracefully().sync();
        }

        return null;
    }

    static class ClientDecoderHandler extends ByteToMessageDecoder {
        /***
         * 每当有新数据接收的时候，ByteToMessageDecoder 都会调用 decode() 方法来处理内部的那个累积缓冲
         * ByteToMessageDecoder 将会丢弃在累积缓冲里已经被读过的数据。
         * 请记得你不需要对多条消息调用 decode()，ByteToMessageDecoder 会持续调用 decode() 直到不放任何数据到 out 里
         * @param ctx
         * @param in
         * @param out
         * @throws Exception
         */
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " decode");
            if (in.readableBytes() < 512) {
                out.add(in.readBytes(in.readableBytes()));
                System.out.println("out final : " + out.size());
                return;
            }
            out.add(in.readBytes(512));
            System.out.println("out : " + out.size());
        }
    }

    static class ClientReplayingHandler extends ReplayingDecoder<Void> {
        /**
         * 另一个解码器: ReplayingDecoder
         *
         * @param ctx
         * @param in
         * @param out
         * @throws Exception
         */
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " decode");
            if (in.readableBytes() < 512) {
                out.add(in.readBytes(in.readableBytes()));
                return;
            }
            out.add(in.readBytes(512));
            System.out.println("out : " + out.size());
        }
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
            System.out.println("" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelRead");

            // 收的数据都应该被累积在 total 变量里
            ByteBuf in = (ByteBuf) msg;
            System.out.println("in : " + in.readableBytes());
            total.writeBytes(in);
            in.release();
            System.out.println("total : " + total.readableBytes());
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
}
