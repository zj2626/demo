package com.demo.common.service.httpclient.abs;

import com.demo.common.service.httpclient.HttpClientDemo;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;

public abstract class Request {
    protected HttpClientDemo clientDemo;
    protected CloseableHttpClient httpClient;

    public String doRequest(Map<String, String> parameter) throws Exception {
        return null;
    }
}
