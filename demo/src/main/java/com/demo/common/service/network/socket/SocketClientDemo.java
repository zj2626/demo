package com.demo.common.service.network.socket;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketClientDemo {

    @Test
    public void client()  {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 10801);

            String data = " this is my 数据 \n\t\t\t\t~~~ 啦啦 done..";
            OutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.write(data.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
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
