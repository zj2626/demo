package com.demo.common.service.net.socket;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketServerDemo {

    /**
     * 1) 先将文件内容从磁盘中拷贝到操作系统buffer
     * 2) 再从操作系统buffer拷贝到程序应用buffer
     * 3) 从程序buffer拷贝到socket buffer
     * 4) 从socket buffer拷贝到协议引擎.
     */
    @Test
    public void server() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10801);
            System.out.println("绑定端口为：" + serverSocket.getLocalPort());

            while (true) {
                // 阻塞等待连接
                Socket socket = serverSocket.accept();
                System.out.println("连接请求地址: " + socket.getInetAddress() + " : " + socket.getPort());
                DataInputStream input = new DataInputStream(socket.getInputStream());
                // 接收数据
                byte[] byteArray = new byte[1024];
                while (true) {
                    int nread = input.read(byteArray, 0, 256);
                    if (-1 == nread) {
                        break;
                    }
                }
                System.out.println("接收到的数据: " + new String(byteArray, StandardCharsets.UTF_8));
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != serverSocket) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
