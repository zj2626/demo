package com.demo.common.service.network.netty;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

public class DemoServer extends MyNettyAddr {

    @Test
    public void test() {
        // cpu核心数
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(NettyRuntime.availableProcessors());
        // netty服务端启动线程数
        System.out.println(SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    }

    @Test
    public void server() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet(1800L);
        excutorPool.futureCancel();
        System.out.println("canceled");
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        /**
         * interface EventLoopGroup extends EventExecutorGroup extends ScheduledExecutorService extends ExecutorService
         * 配置服务端的 NIO 线程池,用于网络事件处理，实质上他们就是 Reactor 线程组
         * bossGroup 用于服务端接受客户端连接，workerGroup 用于进行 SocketChannel 网络读写*/
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);

        try {
            /*启动 NIO 服务端*/
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 注册
                            socketChannel.pipeline()
                                    .addLast(new MyTimeServerHandler())
                            ;
                        }
                    });
            ChannelFuture future = bootstrap.bind(serverPort).sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 阻塞");
            future.channel().closeFuture().sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*释放NIO线程组*/
            if (!bossGroup.isShutdown()) {
                bossGroup.shutdownGracefully();
            }
            if (!workerGroup.isShutdown()) {
                workerGroup.shutdownGracefully();
            }
            workerGroup.shutdownGracefully();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 关闭");
        }

        return null;
    }

    static class MyTimeServerHandler extends ChannelInboundHandlerAdapter {
        private int version;

        public MyTimeServerHandler() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            version = new Random().nextInt(100000);
            System.out.println("\n" + LocalDateTime.now() + " " + Thread.currentThread().getName() + " 构造 " + version);
        }

        /**
         * 收到客户端消息，自动触发
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelRead " + version);

            /* 将 msg 转为 Netty 的 ByteBuf 对象，类似 JDK 中的 java.nio.ByteBuffer，不过 ButeBuf 功能更强，更灵活
             */
            ByteBuf buf = (ByteBuf) msg;
            /* readableBytes：获取缓冲区可读字节数,然后创建字节数组
             * 从而避免了像 java.nio.ByteBuffer 时，只能盲目的创建特定大小的字节数组，比如 1024
             * */
            byte[] reg = new byte[buf.readableBytes()];
            /* readBytes：将缓冲区字节数组复制到新建的 byte 数组中
             * 然后将字节数组转为字符串
             * */
            buf.readBytes(reg);
            String body = new String(reg, StandardCharsets.UTF_8);
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 客户端发送消息 : " + body + " " + version);
            Thread.sleep(1000);

            /* 回复消息
             * copiedBuffer：创建一个新的缓冲区，内容为里面的参数
             * 通过 ChannelHandlerContext 的 write 方法将消息异步发送给客户端
             * */
            String respMsg = "I am Server，消息接收 success!";
            ByteBuf respByteBuf = Unpooled.copiedBuffer(respMsg.getBytes());
            ctx.write(respByteBuf);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelReadComplete " + version);
            /*
             * flush：将消息发送队列中的消息写入到 SocketChannel 中发送给对方，为了频繁的唤醒 Selector 进行消息发送
             * Netty 的 write 方法并不直接将消息写如 SocketChannel 中，调用 write 只是把待发送的消息放到发送缓存数组中，再通过调用 flush
             * 方法，将发送缓冲区的消息全部写入到 SocketChannel 中
             *
             * */
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " exceptionCaught " + version);
            cause.printStackTrace();
            /* 当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
            ctx.close();
        }
    }
}
