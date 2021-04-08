package com.demo.common.service.httpclient;

import com.demo.common.service.httpclient.client.HttpClientAsyncDemo;
import com.demo.common.service.httpclient.abs.Request;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.concurrent.FutureCallback;
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

/**
 * 异步调用httpClient (真异步)
 *
 * HttpAsyncClient则使用Java NIO的异步非阻塞事件驱动I/O模型
 *
 * 基于Java NIO的异步，当发起请求后，调用方不会使用任何线程同步等待http服务端的响应结果（少量的NIO线程不算哦，因为其个数固定，并且不随并发请求数量变化），
 * 而是会使用少量内存来记录请求信息，以便服务端响应结果回来后，可以找到对应的回调函数进行执行。
 */
public class RequestGet_Async extends Request {

    @Test
    public void test() throws InterruptedException {
        clientAsyncDemo = new HttpClientAsyncDemo(this);
        httpClientAsync = HttpClientAsyncDemo.httpClient;

        httpClientAsync.start();

        clientAsyncDemo.execute();
    }

    @Override
    public String doRequest() {
        Map<String, String> parameter = makeRequestParam();

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
        clientAsyncDemo.makeJSONHeader(httpGet);

        // 执行请求
        try {
            System.out.println(httpGet.getURI());
            httpClientAsync.execute(httpGet, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse httpResponse) {
                    // 从响应中获得数据
                    if (null != httpResponse) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        if (200 == httpResponse.getStatusLine().getStatusCode()) {
                            // 响应数据字符串
                            try {
                                System.out.println(EntityUtils.toString(httpEntity));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void failed(Exception e) {

                }

                @Override
                public void cancelled() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    private Map<String, String> makeRequestParam() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("id", "m32nvpfaagcmf");
        parameter.put("kitchenId", "metu8341dq0a5");
        parameter.put("name", "品类一001");
        parameter.put("skuStatus", "1");
        return parameter;
    }
}
