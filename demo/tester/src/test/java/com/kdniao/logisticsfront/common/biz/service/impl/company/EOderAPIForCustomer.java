/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.common.biz.service.impl.company;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电子面单
 * @author wensl
 * @version $Id: KdniaoOnlineOrderAPI.java, v 0.1 2017年12月8日 下午10:33:41 wensl Exp $
 */
public class EOderAPIForCustomer {

    //DEMO
    public static void main(String[] args) {
        EOderAPIForCustomer api = new EOderAPIForCustomer();
        try {
            String result = api.getOrderTracesByJson("", "");
            System.out.println(result);
            JSONObject parseObject = JSON.parseObject(result);
            String printTemplate = parseObject.getString("PrintTemplate");
            String string = parseObject.getString("SubPrintTemplates");
            List object = parseObject.getObject("SubPrintTemplates", List.class);

            System.out.println(printTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //电商ID
    private String EBusinessID = "EBusinessID";
    private String AppKey = "AppKey";

    //请求url
    private String ReqURL="http://api.kdniao.com/api/EOrderService";

    /**
     * Json方式 查询订单物流轨迹
     * @throws Exception CustomerName
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception {
        String OrderCode = System.currentTimeMillis() + "";
        String requestData = "{\n" +
                "    \"TemplateType\": 1,\n" +
                "    \"ShipperCode\": \"LHT\",\n" +
                "    \"OrderCode\": \"" + OrderCode + "\",\n" +
                "    \"PayType\": \"1\",\n" +
                "    \"CustomerName\": \"\",\n" +
                "    \"CustomerPwd\": \"\",\n" +
                "    \"SendSite\": \"\",\n" +
                "    \"MonthCode\": \"\",\n" +
                "    \"WeightUnit\": \"K\",\n" +
                "    \"ExpType\": \"1\",\n" +
                "    \"TemplateSize\": \"\",\n" +
                "    \"Sender\": {\n" +
                "        \"Company\": \"Kuai Jin Data China\",\n" +
                "        \"Name\": \"Mr Wang\",\n" +
                "        \"Email\": \"em\",\n" +
                "        \"Tel\": \"854230478\",\n" +
                "        \"TelExtension\": \"2305\",\n" +
                "        \"Mobile\": \"15018442396\",\n" +
                "        \"PostCode\": \"101101\",\n" +
                "        \"ProvinceName\": \"BEIJING\",\n" +
                "        \"CityName\": \"BEIJING\",\n" +
                "        \"CountryCode\": \"JP\",\n" +
                "        \"CountryName\": \"CHINA\",\n" +
                "        \"Address\": \"Kuai Jin Data China\",\n" +
                "        \"Address2\": \"Unit 5 Beddington Cross\",\n" +
                "        \"Address3\": \"Beddington Farm Road\"\n" +
                "    },\n" +
                "    \"Receiver\": {\n" +
                "        \"FaxNumber\": \"FaxNumber\",\n" +
                "        \"Email\": \"123456789@qq.com\",\n" +
                "        \"Company\": \"IBM Bruse Pte Ltd\",\n" +
                "        \"Name\": \"Li Xi\",\n" +
                "        \"Tel\": \"07558541247\",\n" +
                "        \"TelExtension\": \"2305\",\n" +
                "        \"Mobile\": \"18826732223\",\n" +
                "        \"ProvinceName\": \"上海市\",\n" +
                "        \"CityName\": \"上海市\",\n" +
                "        \"CountryCode\": \"CN\",\n" +
                "        \"CountryName\": \"CHINA\",\n" +
                "        \"Address\": \"上海市青浦区赵巷镇镇中路52弄\",\n" +
                "        \"Address2\": \"3th Floor\",\n" +
                "        \"Address3\": \"The IBM Place\"\n" +
                "    },\n" +
                "    \"Commodity\": [\n" +
                "        {\n" +
                "            \"GoodsName\": \"花-飞-玫-瑰\",\n" +
                "            \"GoodsPrice\": 100.21,\n" +
                "        },\n" +
                "        {\n" +
                "            \"GoodsName\": \"IBM Computer\",\n" +
                "            \"GoodsPrice\": 100.21,\n" +
                "            \"GoodsCode\": \"HS5210313\",\n" +
                "            \"GoodsWeight\": 1,\n" +
                "        }\n" +
                "    ],\n" +
                "    \"IsReturnPrintTemplate\": 1\n" +
                "}";

        System.out.println(requestData);
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1007");
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));

        params.put("DataType", "2");

        System.out.println(urlEncoder(requestData, "UTF-8"));
        System.out.println(urlEncoder(dataSign, "UTF-8"));
        System.out.println();

        String result = sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......

        return result;
    }

    /**
     * MD5加密
     * @param str 内容       
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
     * @param str 内容       
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
     * @param content 内容   
     * @param keyValue Appkey  
     * @param charset 编码方式
     * @throws UnsupportedEncodingException ,Exception
     * @return DataSign签名
     */
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
    * 向指定 URL 发送POST方法的请求     
    * @param url 发送请求的 URL    
    * @param params 请求的参数集合     
    * @return 远程资源的响应结果
    */
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数            
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    //System.out.println(entry.getKey()+":"+entry.getValue());
                }
                //System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
                                                           'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
                                                           '4', '5', '6', '7', '8', '9', '+', '/' };

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
}