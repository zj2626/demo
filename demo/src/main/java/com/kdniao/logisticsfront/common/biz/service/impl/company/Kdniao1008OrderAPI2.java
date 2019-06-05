/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.common.biz.service.impl.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Kdniao1008OrderAPI2 {

    //DEMO
    public static void main(String[] args) throws IOException {
        String[] resultData = toArrayByInputStreamReader1("H://订单.txt");
        int rowLength = resultData.length;
//        for (int i = 0; i < rowLength; i++) {
//            System.out.println(resultData[i]);
//        }

        Kdniao1008OrderAPI2 api = new Kdniao1008OrderAPI2();
        try {
            for (int i = 0; i < rowLength; i++) {
                System.out.print(resultData[i] + ' ');
                String result = api.getOrderTracesByJson("HTKY", resultData[i]);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String EBusinessID = "1535263";
    private String AppKey = "03489832-1264-474e-9198-fd6ba64a5766";

    private String ReqURL = "http://api.kdniao.com/api/dist";

    /**
     * Json方式 查询订单物流轨迹
     *
     * @throws Exception
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception {
        String requestData = "{\n" +
                "\t\"ShipperCode\": \"" + expCode + "\",\n" +
                "\t\"LogisticCode\": \"" + expNo + "\",\n" +
                "\t\"PayType\": \"1\",\n" +
                "\t\"ExpType\": \"1\",\n" +
                "\t\"CustomerName\": \"\",\n" +
                "\t\"CustomerPwd\": \"\",\n" +
                "\t\"MonthCode\": \"\",\n" +
                "\t\"IsNotice\": \"0\",\n" +
                "\t\"Sender\": {\n" +
                "\t\t\"Name\": \"张健\",\n" +
                "\t\t\"Tel\": \"\",\n" +
                "\t\t\"Mobile\": \"13715276714\",\n" +
                "\t\t\"ProvinceName\": \"广东省\",\n" +
                "\t\t\"CityName\": \"深圳市\",\n" +
                "\t\t\"ExpAreaName\": \" 龙华新区 \",\n" +
                "\t\t\"Address\": \"广东省深圳市龙华新区龙华新区大浪街道富裕新村65号\"\n" +
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

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1008");
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = sendPost(ReqURL, params);

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

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
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