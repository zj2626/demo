package com.demo.common.service.httpclient;

import com.alibaba.fastjson.JSON;
import com.demo.common.service.httpclient.abs.Request;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestStream extends Request {
    @Test
    public void test() throws InterruptedException {
        clientDemo = new HttpClientDemo(this);
        httpClient = HttpClientDemo.httpClient;

        clientDemo.execute();
    }

    @Override
    public String doRequest(Map<String, String> parameter) {
        InputStream is = new ByteArrayInputStream(parameter.toString().getBytes());
        InputStreamEntity ise = new InputStreamEntity(is);

        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(18090)
                    .setPath("/api/stream")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 3. 创建Post请求
        HttpPost httpPost = new HttpPost(uri);
        // 4. 设置请求头信息
//        clientDemo.makeJSONHeader(httpPost);
        // 5. 设置请求体参数
        httpPost.setEntity(ise);

        // 设置响应模型
        CloseableHttpResponse httpResponse = null;
        // 执行请求
        try {
            System.out.println(httpPost.getURI());
            httpResponse = httpClient.execute(httpPost);
            // 从响应中获得数据
            if (null != httpResponse) {
                HttpEntity responseEntity = httpResponse.getEntity();
                if (200 == httpResponse.getStatusLine().getStatusCode()) {
                    // 响应数据字符串
                    return EntityUtils.toString(responseEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientDemo.closeClient(httpResponse);
        }
        return null;
    }
}
