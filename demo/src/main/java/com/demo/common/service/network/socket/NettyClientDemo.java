package com.demo.common.service.network.socket;

import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NettyClientDemo {

    @Test
    public void client()  {
        Socket socket = null;
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 18081));
            socketChannel.configureBlocking(true);// 设置阻塞
            socket = new Socket("localhost", 10801);

            String data = " this is my 数据 \n\t\t\t\t~~~ 啦啦 done";

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(null != socket){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
