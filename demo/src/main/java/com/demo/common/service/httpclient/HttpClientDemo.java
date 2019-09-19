package com.demo.common.service.httpclient;

import com.alibaba.fastjson.JSON;
import com.demo.common.service.mysql.company.ExterfaceInvokeHttpSender;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>${commons-httpclient_version}</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>${fastjson_version}</version>
</dependency>

URL: uniform resource locator    [统一资源定位符]
URI: uniform resource identifier [统一资源标识符]
 */
public class HttpClientDemo {
    private static CloseableHttpClient httpClient;

    static {
        // 1. 获得Http客户端 待完成 ???
        httpClient = HttpClientBuilder.create().build();
    }

    @Test
    public void test() {
        EntityDemo entity = doRequestOne("post");
        System.out.println(JSON.toJSONString(entity));
    }

    private EntityDemo doRequestOne(String requestType) {
        // 请求参数传入
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("page", 5);
        parameter.put("size", 20);
        parameter.put("name", "test张");

        if (HttpGet.METHOD_NAME.equalsIgnoreCase(requestType)) {

            return doGetOne(parameter);

        } else if (HttpPost.METHOD_NAME.equalsIgnoreCase(requestType)) {
            // post请求体信息  中文乱码问题 待解决 ???
            Map<String, String> postParameter = new HashMap<>();
            postParameter.put("id", "9");
            postParameter.put("age", "20");
            postParameter.put("name", "demo张");

            return doPostOne(parameter, postParameter);

        } else {
            // 测试中文乱码问题 待解决 ???

            // post请求体信息
            Map<String, String> postParameter = new HashMap<>();
            postParameter.put("id", "9");
            postParameter.put("age", "20");
            postParameter.put("name", "demo张");

            try {
                ExterfaceInvokeHttpSender httpSender = new ExterfaceInvokeHttpSender();
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Content-Type", "application/json;charset=utf-8");
                httpSender.setHeaderMap(headerMap);
                httpSender.setHostname("http://localhost:18090");
                httpSender.afterPropertiesSet();
                httpSender.send("/api/products", null, JSON.toJSONString(postParameter), HttpPost.METHOD_NAME, postParameter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private EntityDemo doGetOne(Map<String, Object> parameter) {

        // 2. 设置请求参数 拼接请求地址
        URI uri = null;
        try {
            List<NameValuePair> params = new ArrayList<>();
            if (!CollectionUtils.isEmpty(parameter)) {
                parameter.forEach((key, value) -> params.add(new BasicNameValuePair(key, value.toString())));
            }

            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(18090)
                    .setPath("/api/products/4005/reviews")
                    .setParameters(params)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 3. 创建Get请求
        HttpGet httpGet = new HttpGet(uri);

        // 设置响应模型
        CloseableHttpResponse httpResponse = null;
        EntityDemo entity = null;

        // 执行请求
        try {
            httpResponse = httpClient.execute(httpGet);

            // 从响应中获得数据
            if (null != httpResponse) {
                HttpEntity httpEntity = httpResponse.getEntity();

                // 状态码
                if (200 == httpResponse.getStatusLine().getStatusCode()) {
                    // 响应数据字符串
                    String responseStr = EntityUtils.toString(httpEntity);

                    // 响应数据转对象
                    entity = JSON.parseObject(responseStr, EntityDemo.class);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return entity;
    }

    private EntityDemo doPostOne(Map<String, Object> parameter, Map<String, String> postParameter) {

        // 2. 设置请求参数 拼接请求地址
        URI uri = null;
        try {
            List<NameValuePair> params = new ArrayList<>();
            if (!CollectionUtils.isEmpty(parameter)) {
                parameter.forEach((key, value) -> params.add(new BasicNameValuePair(key, value.toString())));
            }

            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(18090)
                    .setPath("/api/products")
                    .setParameters(params)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 3. 创建Post请求
        HttpPost httpPost = new HttpPost(uri);

        // 4. 设置请求体参数
        StringEntity requestEntity = new StringEntity(JSON.toJSONString(postParameter), ContentType.create("utf-8"));
        httpPost.setEntity(requestEntity);

        // 5. 设置请求头信息
        httpPost.setHeader("Content-Type", "application/json");

        // 设置响应模型
        CloseableHttpResponse httpResponse = null;
        EntityDemo entity = null;

        // 执行请求
        try {
            httpResponse = httpClient.execute(httpPost);

            // 从响应中获得数据
            if (null != httpResponse) {
                HttpEntity responseEntity = httpResponse.getEntity();

                // 状态码
                if (200 == httpResponse.getStatusLine().getStatusCode()) {
                    // 响应数据字符串
                    String responseStr = EntityUtils.toString(responseEntity);

                    // 响应数据转对象
                    entity = JSON.parseObject(responseStr, EntityDemo.class);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return entity;
    }
}
