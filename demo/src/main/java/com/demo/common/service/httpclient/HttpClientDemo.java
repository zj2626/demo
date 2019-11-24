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

    public static CloseableHttpClient httpClient;
    public static Request request;

    static {
        httpClient = HttpClientDemoFactory.getHttpsClient();
    }

    public HttpClientDemo(Request request) {
        this.request = request;
    }

    public void execute() throws InterruptedException {
        List<Future> futureList = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            Future future = service.submit(() -> {
                try {
                    Thread.sleep(200);
                    System.out.println(doMakeRequest());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            futureList.add(future);
        }
        futureGet(futureList);
    }

    private String doMakeRequest() throws Exception {
        // 请求参数传入
        Map<String, String> parameter = makeRequestParam();
        // 请求体参数传入
        Map<String, String> postParameter = makePostRequestParam();

        return request.doRequest(postParameter);
    }

    private Map<String, String> makePostRequestParam() {
        Map<String, String> postParameter = new HashMap<>();
        postParameter.put("id", "m32nvpfaagcmf");
        postParameter.put("kitchenId", "metu8341dq0a5");
        postParameter.put("name", "品类一001");
        postParameter.put("skuStatus", "1");
        return postParameter;
    }

    private Map<String, String> makeRequestParam() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("page", "5");
        parameter.put("size", "20");
        parameter.put("id", "get-soooo");
        parameter.put("name", "test张");
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
