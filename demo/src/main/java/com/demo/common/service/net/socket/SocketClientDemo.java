package com.demo.common.service.net.socket;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class SocketClientDemo {

    @Test
    public void client() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 10801);
            OutputStream outputStream = new DataOutputStream(socket.getOutputStream());


            FileInputStream inputStream = new FileInputStream("F:\\code\\demo\\demo\\src\\main\\java\\com\\demo\\common\\service\\network" +
                    "\\socket\\data.txt");
            int size;
            byte[] byteArray = new byte[1024];
            while((size = inputStream.read(byteArray)) != -1){
                outputStream.write(byteArray, 0,  size);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
