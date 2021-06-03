package com.demo.common.service.net.netty.learn3.pojo.test1;

import com.demo.common.service.net.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class DemoClient extends MyNettyAddr {

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
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " decode");
            if (in.readableBytes() < 512) {
                out.add(new Entity(in.readBytes(in.readableBytes()).toString(CharsetUtil.UTF_8)));
            } else {
                out.add(new Entity(in.readBytes(in.readBytes(512)).toString(CharsetUtil.UTF_8)));
            }
            System.out.println("out : " + out.size());
        }
    }

    static class ClientHandler extends ChannelInboundHandlerAdapter {
        private ByteBuf total;

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " handlerAdded");
//            total = ctx.alloc().buffer(1024);
            total = Unpooled.buffer(1024);
//            ByteBuf messageWithFooter = Unpooled.wrappedBuffer(message, footer);


        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " handlerRemoved");
            total.release();
            total = null;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println("" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelRead");

            // 收的数据都应该被累积在 total 变量里 [ Automatic Capacity Extension 自动容量扩展 ]
            Entity in = (Entity) msg;
            total.writeBytes(in.getName().getBytes());
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
