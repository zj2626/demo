/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.common.biz.service.impl.company;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Kdniao1800UploadAPI implements InitializingBean {
    protected CloseableHttpClient httpClient;
    protected int readTimeout = 2000;
    protected int connectionTimeout = 2000;
    protected int maxTotalConnection = 5000;
    protected int retryCount = 0;

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

    //DEMO
    public static void main(String[] args) throws Exception {
        String[] resultData = toArrayByInputStreamReader1("H://订单.txt");
        int rowLength = resultData.length;
//        for (int i = 0; i < 5; i++) {
//            System.out.println(resultData[i]);
//        }

        Kdniao1800UploadAPI api = new Kdniao1800UploadAPI();
        api.afterPropertiesSet();
        try {
            for (int i = 0; i < rowLength; i++) {
                System.out.print(resultData[i] + ' ');
                String result = api.getOrderTracesByJson("STO", resultData[i]);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private String EBusinessID = "1535263";
//    private String AppKey = "03489832-1264-474e-9198-fd6ba64a5766";
//    private String EBusinessID = "1529654";
//    private String AppKey = "bc6fd7a5-e0f7-425e-a693-7414b9cc5cdb";
    private String EBusinessID = "1536679";
    private String AppKey = "862b1b5a-d9ac-4cfd-89fe-347bd2e51be7";
    private String ReqURL = "http://api.freight.kdniao.com/settlement/settlementApiInvoke.json";

//    private String EBusinessID = "1261885";
//    private String AppKey = "0a6a455f-7413-4c8d-a6bc-ca9a2fe8b530";
//    private String ReqURL = "http://192.168.1.19:18080/settlementcenter/settlement/settlementApiInvoke.json";

    /**
     * Json方式 查询订单物流轨迹
     *
     * @throws Exception
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception {
        String requestData = "{\n" +
                "\t\"ShipperCode\": \"" + expCode + "\",\n" +
                "\t\"LogisticCode\": \"" + expNo + "\",\n" +
                "\t\"OrderCode\": \"" + "100630658820002" + "\",\n" +
                "\t\"SenderDate\": \"" + "2019-06-05 17:25:52" + "\",\n" +
                "\t\"PayType\": \"1\",\n" +
                "\t\"ExpType\": \"1\",\n" +
                "\t\"CustomerName\": \"\",\n" +
                "\t\"CustomerPwd\": \"\",\n" +
                "\t\"MonthCode\": \"\",\n" +
                "\t\"IsNotice\": \"0\",\n" +
                "\t\"Sender\": {\n" +
                "\t\t\"Name\": \"王琳\",\n" +
                "\t\t\"Tel\": \"\",\n" +
                "\t\t\"Mobile\": \"13911575870\",\n" +
                "\t\t\"ProvinceName\": \"北京市\",\n" +
                "\t\t\"CityName\": \"北京市\",\n" +
                "\t\t\"ExpAreaName\": \"朝阳区\",\n" +
                "\t\t\"Address\": \"马各庄圆通院内泽美仓储\"\n" +
                "\t},\n" +
                "\t\"Receiver\": {\n" +
                "\t\t\"Name\": \"1255760\",\n" +
                "\t\t\"Tel\": \"\",\n" +
                "\t\t\"Mobile\": \"13800000000\",\n" +
                "\t\t\"ProvinceName\": \"广东省\",\n" +
                "\t\t\"CityName\": \"深圳市\",\n" +
                "\t\t\"ExpAreaName\": \"龙华新区\",\n" +
                "\t\t\"Address\": \"测试地址2\"\n" +
                "\t},\n" +
                "\t\"Commodity\": [{\n" +
                "\t\t\"GoodsName\": \"上传\"\n" +
                "\t}]\n" +
                "}";

        Map<String, Object> params = new HashMap<>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1800");
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = sendGet(params, "");

        //根据公司业务处理返回的信息......

        return result;
    }

    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     *
     * @param content  内容
     * @param keyValue Appkey
     * @param charset  编码方式
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    public String sendGet( Map<String, Object> params,String urlAdd) {
        Map<String, Object> paramMap = new HashMap<>();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {

                if(entry.getValue() != null){
                    paramMap.put(entry.getKey(), entry.getValue().toString());
                }else{
                    paramMap.put(entry.getKey(), "");
                }
            }
        }

        String response = "" ;
        try{
            response =   send(urlAdd, paramMap, "", HttpGet.METHOD_NAME) ;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public String send(String path, Map<String, Object> paramMap, String body, String method) {

        HttpRequestBase request = null;
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(ReqURL);
        } catch (URISyntaxException urise) {
            throw new RuntimeException("url语法错误", urise);
        }

        String url = "" ;
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
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
            httpPost.setEntity(new StringEntity(JSON.toJSONString(paramMap), Charset.forName("UTF-8")));


            request = httpPost;
        } else {
            throw new RuntimeException("不支持的http method[" + method + "]");
        }

//        if (!CollectionUtils.isEmpty(headerMap)) {
//            for (String headerName : headerMap.keySet()) {
//                request.addHeader(headerName, headerMap.get(headerName));
//            }
//        }

        String responseString = null;
        long startTime = System.currentTimeMillis();
        boolean success = false;
        Exception ioe = null;
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            success = true;
            return responseString;
        }catch (Exception e1) {
            ioe = e1;
            throw new RuntimeException("http invoke exception", e1);
        }
        finally {
            Map<String, String> paramMapTemp = new HashMap<>();
            if (paramMap != null) {
                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    paramMapTemp.put(entry.getKey(), entry.getValue().toString());
                }
            }
            request.releaseConnection();
            request.abort();
        }
    }

    private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    public static String[] toArrayByInputStreamReader1(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(name);
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            array[i] = (s);
        }
        // 返回数组
        return array;
    }

}