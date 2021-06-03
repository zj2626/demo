package com.demo.common.service.net.netty.learn4.chat;

import com.demo.common.service.net.netty.abs.MyNettyAddr;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class DemoClientHandler extends MyNettyAddr {

    @Override
    public Object doExcute() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast("handler", new SimpleChatClientHandler())
                            ;
                        }
                    });
            // 启动客户端
            Channel channel = bootstrap.connect(serverHost, serverPort).sync().channel();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 等待输入");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
            group.shutdownGracefully().sync();
        }

        return null;
    }

    static class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
            System.out.println(s);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
            Channel incoming = ctx.channel();
            // 当出现异常就关闭连接
            System.out.println("[" + LocalDateTime.now() + " " + Thread.currentThread().getName() + "] " + incoming.remoteAddress() + " " +
                    "异常: " + cause.getMessage());
            ctx.close();
        }
    }
}