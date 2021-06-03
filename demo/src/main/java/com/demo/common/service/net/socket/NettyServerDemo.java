package com.demo.common.service.net.socket;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NettyServerDemo {

    /**
     * https://blog.csdn.net/lixinkuan328/article/details/93407953
     */
    @Test
    public void server() {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress("localhost", 18081));
            System.out.println("绑定端口为：" + serverSocket.getLocalPort());

            while (true) {
                // 阻塞等待连接
                SocketChannel channel = serverSocketChannel.accept();
                System.out.println("连接请求地址: " + channel.getRemoteAddress());
                channel.configureBlocking(true);// 设置阻塞，接不到就停
                // 接收数据
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while (true) {
                    int nread = channel.read(byteBuffer);
                    if (0 == nread) {
                        break;
                    }
                }
                System.out.println("接收到的数据: " + new String(byteBuffer.array(), 0, byteBuffer.position(), StandardCharsets.UTF_8));
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
