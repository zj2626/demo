package com.demo.common.service.httpclient;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
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
import java.util.concurrent.*;

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
    private static int readTimeout = 5000;
    private static int connectionTimeout = 5000;
    private static int maxTotalConnection = 5000;
    private static int validateAfterInactivityTime = 60000;
    private static int maxPerRoute = 500;

    private static CloseableHttpClient httpClient;

    static {
        // 1. 获得Http客户端 待完成 ???
//        httpClient = HttpClientBuilder
//                .create()
//                .build();

        // 2. 获得Http客户端
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, (chain, authType) -> true);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslsf)
                    .build();
            ManagedHttpClientConnectionFactory connFactory =
                    new ManagedHttpClientConnectionFactory(DefaultHttpRequestWriterFactory.INSTANCE,
                            DefaultHttpResponseParserFactory.INSTANCE);
            DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
            PoolingHttpClientConnectionManager manager =
                    new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            manager.setDefaultSocketConfig(socketConfig);
            manager.setMaxTotal(maxTotalConnection);
            manager.setDefaultMaxPerRoute(maxPerRoute);
            manager.setValidateAfterInactivity(validateAfterInactivityTime);
            RequestConfig defaulRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectionTimeout)
                    .setSocketTimeout(readTimeout)
                    .setConnectionRequestTimeout(connectionTimeout)
                    .build();

            httpClient = HttpClients.custom()
                    .setConnectionManager(manager)
                    .setConnectionManagerShared(false)
                    .evictIdleConnections(60, TimeUnit.SECONDS)
                    .evictExpiredConnections()
                    .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                    .setDefaultRequestConfig(defaulRequestConfig)
                    .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                    .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                    .setRetryHandler((exception, executionCount, context) -> {
                        if (executionCount > 2) {
                            return false;
                        }
                        return exception instanceof NoHttpResponseException;
                    })
                    .build();
            Runtime.getRuntime()
                    .addShutdownHook(new Thread() {
                        @Override
                        public void run() {
                            try {
                                httpClient.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws InterruptedException {
        List<Future> futureList = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            Future future = service.submit(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(doMakeRequest(HttpPost.METHOD_NAME));
            });
            futureList.add(future);
        }
        futureGet(futureList);
    }

    private String doMakeRequest(String requestType) {
        // 请求参数传入
        Map<String, String> parameter = makeRequestParam();
        // 请求体参数传入
        Map<String, String> postParameter = makePostRequestParam();

        if (HttpPost.METHOD_NAME.equalsIgnoreCase(requestType)) {
            // Post请求
            return doPostOne(parameter, postParameter);
        } else {
            // Get请求
            return doGetOne(parameter);
        }
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
        parameter.put("name", "test张");
        return parameter;
    }

    /**
     * 设置请求头
     *
     * @param httpRequestBase
     */
    private void makeHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Content-Type", "application/json");
    }

    private String doGetOne(Map<String, String> parameter) {
        // 2. 设置请求参数 拼接请求地址
        URI uri = null;
        try {
            List<NameValuePair> params = new ArrayList<>();
            if (!CollectionUtils.isEmpty(parameter)) {
                parameter.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
            }
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8089)
                    .setPath("/invoke")
                    .setParameters(params)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 3. 创建Get请求
        HttpGet httpGet = new HttpGet(uri);
        // 设置请求头信息
        makeHeader(httpGet);

        // 设置响应模型
        CloseableHttpResponse httpResponse = null;
        // 执行请求
        try {
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
            closeClient(httpResponse);
        }
        return null;
    }

    private String doPostOne(Map<String, String> parameter, Map<String, String> postParameter) {
        // 2. 设置请求参数 拼接请求地址
        URI uri = null;
        try {
            List<NameValuePair> params = new ArrayList<>();
            if (!CollectionUtils.isEmpty(parameter)) {
                parameter.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
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
        // 4. 设置请求头信息
        makeHeader(httpPost);
        // 5. 设置请求体参数
        httpPost.setEntity(new StringEntity(JSON.toJSONString(postParameter), "UTF-8"));

        // 设置响应模型
        CloseableHttpResponse httpResponse = null;
        // 执行请求
        try {
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
            closeClient(httpResponse);
        }
        return null;
    }

    private void closeClient(CloseableHttpResponse httpResponse) {
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
