package hello.request;


import com.alibaba.fastjson.JSON;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 调用网点宝工具
 *
 * @author dev
 * @version $Id: ExterfaceInvokeIOHttpSender.java, v 0.1 2018年5月15日 上午10:37:07 dev Exp $
 */
public class ExterfaceInvokeIOHttpSender implements InitializingBean {

    protected String appName;
    protected CloseableHttpClient httpClient;
    protected String contentType = "application/json; charset=UTF-8";
    protected Map<String, String> headerMap;
    protected String hostname;
    protected int readTimeout = 2000;
    protected int connectionTimeout = 2000;
    protected int maxTotalConnection = 5000;
    protected int retryCount = 0;
    protected String senderType;

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
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(this.readTimeout).setConnectTimeout(this.connectionTimeout).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加到100
        cm.setMaxTotal(this.maxTotalConnection);
        // 也设置为最大连接数
        cm.setDefaultMaxPerRoute(maxTotalConnection);
        // 重试次数，retryCount为0表示不执行重试
        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryCount, false);
        httpClient = HttpClients.custom().setRetryHandler(retryHandler).setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }

    public String sendGet(Map<String, Object> params, String urlAdd) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {

                if (entry.getValue() != null) {
                    paramMap.put(entry.getKey(), entry.getValue().toString());
                } else {
                    paramMap.put(entry.getKey(), "");
                }
            }
        }

        String response = "";
        response = send(urlAdd, paramMap, "", HttpGet.METHOD_NAME);

        return response;
    }

    /**
     * 请求网点宝POST形式
     *
     * @param params
     * @return
     */
    public String sendPost(Map<String, Object> params, String urlAdd) throws Exception {
        return send(urlAdd, params, "", HttpPost.METHOD_NAME);
    }

    public String send(String path, Map<String, Object> paramMap, String body, String method) throws Exception {

        HttpRequestBase request = null;
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(hostname);
        } catch (URISyntaxException urise) {
            urise.printStackTrace();
        }

        String url = "";
        if (HttpGet.METHOD_NAME.equals(method)) {
            if (!StringUtils.isEmpty(path)) {
                uriBuilder.setPath(path);
            }
            if (!CollectionUtils.isEmpty(paramMap)) {
                for (String paramName : paramMap.keySet()) {
                    uriBuilder.addParameter(paramName, paramMap.get(paramName).toString());
                }
            }
            url = uriBuilder.toString();
            HttpGet httpGet = new HttpGet(url);
            request = httpGet;
        } else if (HttpPost.METHOD_NAME.equals(method)) {
            if (!StringUtils.isEmpty(path)) {
                uriBuilder.setPath(path);
            }

            url = uriBuilder.toString();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", contentType);
            httpPost.setEntity(new StringEntity(JSON.toJSONString(paramMap), Charset.forName("UTF-8")));


            request = httpPost;
        } else {
            return null;
        }

        if (!CollectionUtils.isEmpty(headerMap)) {
            for (String headerName : headerMap.keySet()) {
                request.addHeader(headerName, headerMap.get(headerName));
            }
        }
        String responseString = null;
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            return responseString;
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            Map<String, String> paramMapTemp = new HashMap<String, String>();
            if (paramMap != null) {
                for (Entry<String, Object> entry : paramMap.entrySet()) {
                    paramMapTemp.put(entry.getKey(), entry.getValue().toString());
                }
            }
            request.releaseConnection();
            request.abort();
        }
    }

    public static void main(String[] args) {
        String url = "http://192.168.1.109:9509/BasicData.CommonDataBLL.GetTypeList.api";
        ExterfaceInvokeIOHttpSender e = new ExterfaceInvokeIOHttpSender();
        e.setHostname(url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("DataType", "SETTLEMENT_TYPE");
        //e.send(url, params, "", "GET");
        //e.sendJson(params);
    }
}
