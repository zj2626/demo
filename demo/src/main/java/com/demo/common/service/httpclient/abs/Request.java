package com.demo.common.service.httpclient.abs;

import com.demo.common.service.httpclient.client.HttpClientAsyncDemo;
import com.demo.common.service.httpclient.client.HttpClientDemo;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import java.util.Map;

public abstract class Request {
    protected HttpClientDemo clientDemo;
    protected HttpClientAsyncDemo clientAsyncDemo;
    protected CloseableHttpClient httpClient;
    protected CloseableHttpAsyncClient httpClientAsync;

    public abstract String doRequest() throws Exception;
}
