package com.demo.common.service.httpclient;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ExterfaceInvokeHttpSender implements InitializingBean {
    
    protected static final Logger logger = LoggerFactory.getLogger("remote.digest");
    protected String appName;
    protected CloseableHttpClient httpClient;
    protected String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
    protected Map<String, String> headerMap;
    protected String hostname;
    protected int readTimeout = 5000;
    protected int connectionTimeout = 5000;
    protected int maxTotalConnection = 5000;
    protected int retryCount = 0;
    protected String senderType;
    protected int validateAfterInactivityTime = 60000;
    private int maxPerRoute = 500;
    
    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }
    
    public void setValidateAfterInactivityTime(int validateAfterInactivityTime) {
        this.validateAfterInactivityTime = validateAfterInactivityTime;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
    
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
    
    public void setMaxTotalConnection(int maxTotalConnection) {
        this.maxTotalConnection = maxTotalConnection;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
    
    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }
    
    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, (chain, authType) -> true);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http",
                PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslsf).build();
        ManagedHttpClientConnectionFactory connFactory = new ManagedHttpClientConnectionFactory(DefaultHttpRequestWriterFactory.INSTANCE,
                DefaultHttpResponseParserFactory.INSTANCE);
        DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory,
                dnsResolver);
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        
        manager.setDefaultSocketConfig(socketConfig);
        manager.setMaxTotal(maxTotalConnection);
        manager.setDefaultMaxPerRoute(maxPerRoute);
        manager.setValidateAfterInactivity(validateAfterInactivityTime);
        RequestConfig defaulRequestConfig =
                RequestConfig.custom().setConnectTimeout(connectionTimeout).setSocketTimeout(readTimeout).setConnectionRequestTimeout(connectionTimeout)
                .build();
        
        httpClient = HttpClients.custom().setConnectionManager(manager).setConnectionManagerShared(false).evictIdleConnections(60,
                TimeUnit.SECONDS).evictExpiredConnections()
                .setConnectionTimeToLive(60, TimeUnit.SECONDS).setDefaultRequestConfig(defaulRequestConfig).setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE).setRetryHandler((exception, executionCount, context) -> {
                    if (executionCount > 2) {
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {
                        logger.warn("NoHttpResponseException on" + executionCount + "call");
                        return true;
                    }
                    return false;
                }).build();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public String send(String body) {
        return this.send(null, body, HttpPost.METHOD_NAME);
    }
    
    public String send(Map<String, String> paramMap, String body, String method) {
        return this.send(null, paramMap, body, method, null);
    }
    
    public String sendForm(Map<String, String> paramMap, Map<String, String> formMap) {
        return this.send(null, paramMap, null, HttpPost.METHOD_NAME, formMap);
    }
    
    public String send(String path, Map<String, String> paramMap, String body, String method) {
        return this.send(path, paramMap, body, method, null);
    }
    
    public String send(String path, Map<String, String> paramMap, String body, String method, Map<String, String> formMap) {
        HttpRequestBase request = null;
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(hostname);
        } catch (URISyntaxException urise) {
            throw new RuntimeException("url语法错误", urise);
        }
        if (!StringUtils.isEmpty(path)) {
            uriBuilder.setPath(path);
        }
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (String paramName : paramMap.keySet()) {
                uriBuilder.addParameter(paramName, paramMap.get(paramName));
            }
        }
        String url = uriBuilder.toString();
        try {
            if (HttpGet.METHOD_NAME.equals(method)) {
                HttpGet httpGet = new HttpGet(url);
                request = httpGet;
            } else if (HttpPost.METHOD_NAME.equals(method)) {
                HttpPost httpPost = new HttpPost(url);
                if (StringUtils.isEmpty(body) && null != formMap) {
                    List<NameValuePair> paramList = new ArrayList<>();
                    for (String key : formMap.keySet()) {
                        paramList.add(new BasicNameValuePair(key, formMap.get(key)));
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
                } else {
                    httpPost.setEntity(new StringEntity(body, ContentType.create(contentType)));
                }
                request = httpPost;
            } else {
                throw new RuntimeException("不支持的http method[" + method + "]");
            }
            
            if (!CollectionUtils.isEmpty(headerMap)) {
                for (String headerName : headerMap.keySet()) {
                    request.addHeader(headerName, headerMap.get(headerName));
                }
            }
        } catch (Exception e1) {
            throw new RuntimeException("Unsupported  http exception", e1);
        }
        String responseString = null;
        long startTime = System.currentTimeMillis();
        boolean success = false;
        IOException ioe = null;
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            success = true;
            return responseString;
        } catch (ConnectTimeoutException cte) {
            ioe = cte;
            throw new RuntimeException("http connect timeout exception", cte);
        } catch (SocketTimeoutException ste) {
            ioe = ste;
            throw new RuntimeException("http read timeout exception", ste);
        } catch (IOException e) {
            ioe = e;
            throw new RuntimeException("http invoke exception", e);
        } finally {
            request.releaseConnection();
            request.abort();
        }
    }
    
    public byte[] sendStream(String path, Map<String, String> paramMap, String body, String method, Map<String, String> formMap) {
        HttpRequestBase request;
        URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(hostname);
        } catch (URISyntaxException urise) {
            throw new RuntimeException("url语法错误", urise);
        }
        if (!StringUtils.isEmpty(path)) {
            uriBuilder.setPath(path);
        }
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (String paramName : paramMap.keySet()) {
                uriBuilder.addParameter(paramName, paramMap.get(paramName));
            }
        }
        String url = uriBuilder.toString();
        try {
            if (HttpGet.METHOD_NAME.equals(method)) {
                HttpGet httpGet = new HttpGet(url);
                request = httpGet;
            } else if (HttpPost.METHOD_NAME.equals(method)) {
                HttpPost httpPost = new HttpPost(url);
                if (StringUtils.isEmpty(body) && null != formMap) {
                    List<NameValuePair> paramList = new ArrayList<>();
                    for (String key : formMap.keySet()) {
                        paramList.add(new BasicNameValuePair(key, formMap.get(key)));
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
                } else {
                    httpPost.setEntity(new StringEntity(body, ContentType.create(contentType)));
                }
                request = httpPost;
            } else {
                throw new RuntimeException("不支持的http method[" + method + "]");
            }
            
            if (!CollectionUtils.isEmpty(headerMap)) {
                for (String headerName : headerMap.keySet()) {
                    request.addHeader(headerName, headerMap.get(headerName));
                }
            }
        } catch (Exception e1) {
            throw new RuntimeException("Unsupported  http exception", e1);
        }
        String responseString = null;
        long startTime = System.currentTimeMillis();
        boolean success = false;
        IOException ioe = null;
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            success = true;
            return EntityUtils.toByteArray(response.getEntity());
        } catch (ConnectTimeoutException cte) {
            ioe = cte;
            throw new RuntimeException("http connect timeout exception", cte);
        } catch (SocketTimeoutException ste) {
            ioe = ste;
            throw new RuntimeException("http read timeout exception", ste);
        } catch (IOException e) {
            ioe = e;
            throw new RuntimeException("http invoke exception", e);
        } finally {
            request.releaseConnection();
            request.abort();
        }
    }
}