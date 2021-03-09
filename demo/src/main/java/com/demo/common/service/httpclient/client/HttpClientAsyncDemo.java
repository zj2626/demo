package com.demo.common.service.httpclient.client;

import com.demo.common.service.httpclient.HttpClientDemoFactory;
import com.demo.common.service.httpclient.abs.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
public class HttpClientAsyncDemo {
    public static CloseableHttpAsyncClient httpClient;
    private static Request request;

    public HttpClientAsyncDemo(Request request) {
        // 这里简化了 其实也可以像 @see:com.demo.common.service.httpclient.HttpClientDemoFactory 一样
        httpClient = HttpAsyncClients.custom().build();;
        HttpClientAsyncDemo.request = request;
    }

    public void execute() throws InterruptedException {
        List<Future> futureList = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1; i++) {
            Future future = service.submit(() -> {
                try {
                    Thread.sleep(200);
                    System.out.println(request.doRequest(makeRequestParam()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            futureList.add(future);
        }
        futureGet(futureList);
    }

    private Map<String, String> makeRequestParam() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("id", "m32nvpfaagcmf");
        parameter.put("kitchenId", "metu8341dq0a5");
        parameter.put("name", "品类一001");
        parameter.put("skuStatus", "1");
        return parameter;
    }

    /**
     * 设置请求头
     *
     * @param httpRequestBase
     */
    public void makeJSONHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Content-Type", "application/json");
    }

    public void makeFormHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public void makeXMLHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Content-Type", "text/xml");
    }

    /**
     * 文件上传不需要手动设置该请求头
     * @param httpRequestBase
     */
    public void makeMultipartHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Content-Type", "multipart/form-data");
    }

    public void closeClient(CloseableHttpResponse httpResponse) {
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

    private void futureGet(List<Future> futureList) throws InterruptedException {
        for (Future future : futureList) {
            try {
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
