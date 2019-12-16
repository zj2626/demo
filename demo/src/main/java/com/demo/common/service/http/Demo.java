package com.demo.common.service.http;

import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Demo {
    String url = "https://csdnimg.cn/cdn/content-toolbar/csdn-logo_.png";

    @Test
    public void test(){
        HttpURLConnection conn = null;
        InputStream inputStream = null;

        try {
            // 建立链接
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            // 默认get方式
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            // 连接指定的资源
            conn.connect();
            // 获取网络输入流
            inputStream = conn.getInputStream();
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
