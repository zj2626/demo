package com.demo.common.service.network.socket;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
                //byte[] byteArray = new byte[4096];
                // 接收数据
                ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                while (true) {
                    int nread = channel.read(byteBuffer);
                    if (-1 == nread) {
                        break;
                    }
                    byte[] array = byteBuffer.array();
                    String string = new String(array, 0, byteBuffer.position());
                    System.out.print(string);
                    byteBuffer.clear();
                }
                //System.out.println("接收到的数据: " + new String(byteArray, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
