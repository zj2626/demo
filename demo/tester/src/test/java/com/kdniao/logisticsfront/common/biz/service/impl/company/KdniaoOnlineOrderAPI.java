/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.common.biz.service.impl.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * 预约取件
 * @author wensl
 * @version $Id: KdniaoOnlineOrderAPI.java, v 0.1 2017年12月8日 下午10:33:41 wensl Exp $
 */
public class KdniaoOnlineOrderAPI {

    //DEMO
    public static void main(String[] args) {
        KdniaoOnlineOrderAPI api = new KdniaoOnlineOrderAPI();
        try {
            //            for (int i = 0; i < 1; i++) {     
            //                System.out.println(i);
            //                String result = api.getOrderTracesByJson("KDN", UUID.randomUUID().toString());
            //                //String result = api.getOrderTracesByJson("SF", "dddddd");
            //                System.out.print(result);
            //            }
            //String result = api.getOrderTracesByJson("DHL", "5376565950");
            String result = api.getOrderTracesByJson("CND", "");
            System.out.print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //电商ID
    //private String EBusinessID = "1279441";

    //private String EBusinessID = "1264635";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    //private String AppKey = "af5c859e-9741-452c-a908-140a25aba90f";
    //private String AppKey = "aca4059d-8e36-4d9d-ad5a-0aceb47c795a";

    //    private String EBusinessID = "1261557";
    //    private String AppKey = "8c3103d8-5549-437d-a3a5-e5b0c05bfa32";
    private String EBusinessID = "1279441";
    private String AppKey = "af5c859e-9741-452c-a908-140a25aba90f";

    //请求url
    //private String ReqURL="http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
    //private String ReqURL="http://localhost:8084/openapi/gateway/exterfaceInvoke.json";   
    private String ReqURL = "http://192.168.1.15:8080/openapi/gateway/exterfaceInvoke.json";
    //    private String ReqURL = "http://api.kdniao.cc/api/OOrderService";

    /**
     * Json方式 查询订单物流轨迹
     * @throws Exception 
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception {
        //String requestData= "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";
        //        String requestData1 = "{\"OrderCode\":\"1a116119214\",\"ShipperCode\":\"" + expCode + "\",\"LogisticCode\":\"" + expNo
        //                              + "\",\"MonthCode\":\"\",\"PayType\":1,\"ExpType\":1,\"Cost\":1,\"OtherCost\":0,\"Receiver\":{\"Company\":\"快金1\",\"Name\":\"张三1\",\"Mobile\":\"18898367995\",\"PostCode\":\"518000\",\"ProvinceName\":\"上海市\",\"CityName\":\"上海市\",\"ExpAreaName\":\"青浦区\",\"Address\":\"保税区宝华1号603\"},\"Sender\":{\"Company\":\"韵达2\",\"Name\":\"李2\",\"Mobile\":\"15112648993\",\"PostCode\":\"518123\",\"ProvinceName\":\"广东省\",\"CityName\":\"深圳市\",\"ExpAreaName\":\"南山区\",\"Address\":\"大新村74栋1204\"},\"StartDate\":\"2018-04-19 15:28:00\",\"EndDate\":\"2018-04-19 15:50:00\",\"Weight\":1,\"Quantity\":1,\"Volume\":1,\"Remark\":\"瞧瞧\",\"Commodity\":[{\"GoodsName\":\"TV20180227\"}]}";
        String requestData = "{\"OrderCode\":\"201808160000\",\"ShipperCode\":\"" + expCode + "\",\"LogisticCode\":\"" + expNo
                             + "\",\"MonthCode\":\"7555025105\",\"PayType\":1,\"ExpType\":1,\"Receiver\":{\"Name\":\"刘述礼\",\"Mobile\":\"13590292969\",\"ProvinceName\":\"广东省\",\"CityName\":\"深圳市\",\"ExpAreaName\":\"南山区\",\"Address\":\"沙河街道塘头村5坊6号\"},\"Sender\":{\"Name\":\"李旭安\",\"Mobile\":\"15814460948\",\"PostCode\":\"518000\",\"ProvinceName\":\"广东省\",\"CityName\":\"深圳市\",\"ExpAreaName\":\"福田区\",\"Address\":\"保税区金花路29号华宝一号大厦A601\"},\"Quantity\":1,\"Commodity\":[{\"GoodsName\":\"测试下单\"}]}";//HHTT
        //        String requestData = "{\"OrderCode\":\"1rw2Pe2q100sa1q34m2\",\"FcBoxShipperCode\":\"\",\"Remark\":\"无\",\"ShipperCode\":\"FCBOX\",\"LogisticCode\":\"" + expNo
        //                             + "\",\"PayType\":4,\"ExpType\":1,\"Receiver\":{\"Company\":\"深圳快金数据有限公司\",\"Name\":\"李华\",\"Mobile\":\"13590292969\",\"ProvinceName\":\"广东省\",\"CityName\":\"深圳市\",\"ExpAreaName\":\"福田区\",\"Address\":\"保税区金花路29号华宝一号大厦A601\",\"PostCode\":\"518000\"},\"Sender\":{\"Company\":\"深圳市\",\"Name\":\"1234567\",\"Mobile\":\"15817289472\",\"PostCode\":\"518000\",\"ProvinceName\":\"广东省\",\"CityName\":\"深圳市\",\"ExpAreaName\":\"福田区\",\"Address\":\"上沙椰树村6巷5号504\",\"PostCode\":\"518000\"},\"Commodity\":[{\"GoodsName\":\"发票\"}]}";//HHTT

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1001");
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

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