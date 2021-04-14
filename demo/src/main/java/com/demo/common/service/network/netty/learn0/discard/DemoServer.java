package com.demo.common.service.network.netty.learn0.discard;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
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
     * https://www.w3cschool.cn/netty4userguide/3ban1mtr.html
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public Object doExcute() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThread);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new MyServerHandler())
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
            if (!bossGroup.isShutdown()) {
                bossGroup.shutdownGracefully().sync();
            }
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 关闭");
        }

        return null;
    }

    // 丢弃服务器 + 收到的数据
    static class MyServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " channelRead");
            ByteBuf in = ((ByteBuf) msg);
            System.out.println(in.isReadable());
            System.out.println(in.toString(CharsetUtil.UTF_8));

            // 丢弃收到的数据; ByteBuf 是一个引用计数对象，这个对象必须显示地调用 release() 方法来释放
            in.release(); // 等同于 ReferenceCountUtil.release(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " exceptionCaught");
            cause.printStackTrace();
            ctx.close();
        }
    }
}
