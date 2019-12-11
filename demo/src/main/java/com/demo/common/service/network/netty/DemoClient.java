package com.demo.common.service.network.netty;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DemoClient extends MyExcutor {
    private static String serverHost = DemoServer.serverHost;
    private static int serverPort = DemoServer.serverPort;

    @Test
    public void client() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(2);
        excutorPool.futureGet();
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            /* Bootstrap 与 ServerBootstrap 都继承(extends)于 AbstractBootstrap
             * 创建客户端辅助启动类,并对其配置,与服务器稍微不同，这里的 Channel 设置为 NioSocketChannel
             * 然后为其添加 Handler，这里直接使用匿名内部类，实现 initChannel 方法
             * 作用是当创建 NioSocketChannel 成功后，在进行初始化时,将它的ChannelHandler设置到ChannelPipeline中，用于处理网络I/O事件*/
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture caChannelFuture = bootstrap.connect(serverHost, serverPort).sync();
            System.out.println(Thread.currentThread().getName() + " 异步发送");
            caChannelFuture.channel().closeFuture().sync();
            System.out.println(Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*释放NIO线程组*/
            if (!group.isShutdown()) {
                group.shutdownGracefully();
            }
            System.out.println(Thread.currentThread().getName() + " 关闭");
        }

        return null;
    }

    static class TimeClientHandler extends ChannelInboundHandlerAdapter {
        /**
         * 当客户端和服务端 TCP 链路建立成功之后，Netty 的 NIO 线程会调用 channelActive 方法
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println(Thread.currentThread().getName() + " channelActive");

            String sendMsg = Thread.currentThread().getName() + " 来自客户端";
            byte[] reqMsgByte = sendMsg.getBytes(StandardCharsets.UTF_8);
            ByteBuf reqByteBuf = Unpooled.buffer(reqMsgByte.length);
            /*
             * writeBytes：将指定的源数组的数据传输到缓冲区
             * 调用 ChannelHandlerContext 的 writeAndFlush 方法将消息发送给服务器
             */
            reqByteBuf.writeBytes(reqMsgByte);
            ctx.writeAndFlush(reqByteBuf);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(Thread.currentThread().getName() + " channelRead");

            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, StandardCharsets.UTF_8);
            System.out.println(Thread.currentThread().getName() + " 服务端返回消息：" + body);
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println(Thread.currentThread().getName() + " exceptionCaught");
            cause.printStackTrace();
            /* 当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
            ctx.close();
        }
    }
}
