package com.demo.common.service.httpclient;

import com.demo.common.service.httpclient.abs.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

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
public class HttpClientDemo {
    static CloseableHttpClient httpClient;
    private static Request request;

    HttpClientDemo(Request request) {
        httpClient = HttpClientDemoFactory.getHttpsClient();
        HttpClientDemo.request = request;
    }

    public void execute() throws InterruptedException {
        List<Future> futureList = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
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
    void makeJSONHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Content-Type", "application/json");
    }

    void makeFormHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public void makeMultipartHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Content-Type", "multipart/form-data");
    }

    void closeClient(CloseableHttpResponse httpResponse) {
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
