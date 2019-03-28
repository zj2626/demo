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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轨迹查询
 *
 * @author huangboke
 * @version $Id: KdniaoTrackQueryAPI.java, v 0.1 2017年4月8日 下午10:33:41 huangboke Exp $
 */
public class KdniaoTrackQueryAPI implements Runnable {

    private static final String[] acceptTimeDateFormats = new String[]{"yyyy-MM-dd HH:mm:ss"};
    private static long sleep = 500;

    private static int threadNum = 1; //线程数
    private static int resultNum = 0; //请求失败次数
    private static int countNum = 0; //请求次数
    private static long startTime;//请求开始时间

    private String getCurrentIp() {
        return "127.0.0.1";
    }

    private static final String[] list = {"800847735149207245", "814399465169", "815421363381", "814673071657", "815475645729",
            "815171713670"};

    //DEMO
    public static void main(String[] args) {
        request();
    }

    public static void request() {
        startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();
        KdniaoTrackQueryAPI myThread = new KdniaoTrackQueryAPI();
        //多线程
        for (int i = 0; i < threadNum; i++) {
            try {
                Thread td = new Thread(myThread);
                td.start();
                threads.add(td);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Thread td : threads) {
            try {
                td.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //打印请求失败次数
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("请求失败次数为 " + resultNum);
        }

        //请求总时长
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println(time + "毫秒");
        System.out.println(time / 1000 + "秒");
        System.out.println(time / 1000 / 60 + "分");
        System.out.println(time / 1000 / 60 / 60 + "时");
    }

    @Override
    public void run() {
        String currentName = Thread.currentThread().getName();
        System.out.println(currentName + " >start... ");

        int i = 0;
        while (true) {
            int n = (int) (Math.random() * list.length - 1);
            String wayBillCode = list[n];

            long currentStartTime = System.currentTimeMillis();
            try {
                KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
                String result = api.getOrderTracesByJson("YTO", wayBillCode);
                System.out.println(">>>>>>>>成 功" + result);
                System.out.println("> 请求线程数：" + (threadNum + 1) + "个 | 请求总次数：" + (countNum + 1) + "次 | 请求失败总次数：" + (resultNum + 1) + "次 | 当前线程名称：" + Thread.currentThread().getName() + " | 当前线程请求总次数：" + i + "次");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error info," + e.getMessage());
                resultNum++;
            } finally {
                long currentEndTime = System.currentTimeMillis();
                i++;
                countNum++;
                long time = System.currentTimeMillis();
                System.out.println("单号：" + wayBillCode + " > 本次总时长" + (currentEndTime - currentStartTime) + " | 请求总时长：" + (time - startTime) + " == " + (time - startTime) / 1000 + " 秒" + " == " + (time - startTime) / 1000 / 60 + " 分" + " == "
                        + (time - startTime) / 1000 / 60 / 60 + " 时" + "\n");

                if (resultNum >= 350) {
                    throw new RuntimeException("stop --------------");
                }
            }
        }
    }

    //电商ID
    //    private String EBusinessID = "1266103";
    //    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    //    private String AppKey = "cbc60978-e082-420c-80f3-877c1ec7edb4";

    private String EBusinessID = "1257227";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey = "F204758A-513B-4647-98D6-19DE7EA75BA3";

//    private String ReqURL = "http://192.168.1.15:8080/openapi/gateway/exterfaceInvoke.json";
    private String ReqURL = "http://api.kdniao.com/api/dist";

    /**
     * Json方式 查询订单物流轨迹
     *
     * @throws Exception
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception {
        String requestData = "{\"OrderCode\":\"\",\"ShipperCode\":\"" + expCode + "\",\"LogisticCode\":\"" + expNo + "\"}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1002");
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
}