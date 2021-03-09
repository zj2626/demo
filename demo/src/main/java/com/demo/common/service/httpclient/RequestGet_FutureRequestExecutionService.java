package com.demo.common.service.httpclient;

import com.demo.common.service.httpclient.abs.Request;
import com.demo.common.service.httpclient.client.HttpClientDemo;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步调用httpClient (假异步)
 */
public class RequestGet_FutureRequestExecutionService extends Request {
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    private FutureRequestExecutionService futureRequestExecutionService = null;


    @Test
    public void test() throws InterruptedException {
        clientDemo = new HttpClientDemo(this);
        httpClient = HttpClientDemo.httpClient;
        futureRequestExecutionService = new FutureRequestExecutionService(httpClient, executorService);

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
        clientDemo.makeJSONHeader(httpGet);

        // 设置响应模型
        String httpResponse = null;
        // 执行请求
        try {
            System.out.println(httpGet.getURI());
            HttpRequestFutureTask<String> task = futureRequestExecutionService.execute(httpGet, HttpClientContext.create(), new BasicResponseHandler());
            System.out.println("done ~~~");
            System.out.println("done ~~~");
            System.out.println("done ~~~");
            System.out.println("done ~~~");
            System.out.println("done ~~~");

            httpResponse = task.get();
            // 从响应中获得数据
            if (null != httpResponse) {
                return httpResponse;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }
}
