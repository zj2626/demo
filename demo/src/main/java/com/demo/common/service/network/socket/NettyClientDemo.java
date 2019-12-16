package com.demo.common.service.network.socket;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NettyClientDemo {

    @Test
    public void client() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 18081));
            socketChannel.configureBlocking(true);// 设置阻塞

            FileInputStream inputStream = new FileInputStream("F:\\code\\demo\\demo\\src\\main\\java\\com\\demo\\common\\service\\network" +
                    "\\socket\\data.txt");

            FileChannel fileChannel = inputStream.getChannel();
            int offset = 4096;
            long size = fileChannel.size();
            int pos = 0;
            while (pos < size) {
                fileChannel.transferTo(pos, offset, socketChannel);
                pos += offset;
            }
            inputStream.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socketChannel) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String makeData() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < 1024; i++) {
            data.append(" this is my 数据数据数据数据数据数据数据数据数据数据数据数据数据数据数据数据 ~~~ 啦啦 done..\n");
        }
        return data.toString();
    }
}
