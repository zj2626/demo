package com.demo.common.service.httpclient;

import com.demo.common.service.httpclient.abs.Request;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestForm extends Request {
    @Test
    public void test() throws InterruptedException {
        clientDemo = new HttpClientDemo(this);
        httpClient = HttpClientDemo.httpClient;

        clientDemo.execute();
    }

    @Override
    public String doRequest(Map<String, String> parameter) {
        // 2. 设置请求参数 拼接请求地址
        List<NameValuePair> params = new ArrayList<>();
        if (!CollectionUtils.isEmpty(parameter)) {
            parameter.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
        }
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(18080)
                    .setPath("/api/products")
                    .setParameters(params)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 3. 创建Get请求
        HttpGet httpGet = new HttpGet(uri);
        // 设置请求头信息
        clientDemo.makeFormHeader(httpGet);

        // 设置响应模型
        CloseableHttpResponse httpResponse = null;
        // 执行请求
        try {
            System.out.println(httpGet.getURI());
            httpResponse = httpClient.execute(httpGet);
            // 从响应中获得数据
            if (null != httpResponse) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (200 == httpResponse.getStatusLine().getStatusCode()) {
                    // 响应数据字符串
                    return EntityUtils.toString(httpEntity);
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
