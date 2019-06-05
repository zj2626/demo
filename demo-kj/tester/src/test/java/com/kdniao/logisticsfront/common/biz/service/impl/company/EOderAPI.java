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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 电子面单
 * @author wensl
 * @version $Id: KdniaoOnlineOrderAPI.java, v 0.1 2017年12月8日 下午10:33:41 wensl Exp $
 */
public class EOderAPI {

    //DEMO
    public static void main(String[] args) {
        EOderAPI api = new EOderAPI();
        try {
            //            for (int i = 0; i < 1; i++) {     
            //                System.out.println(i);
            //                String result = api.getOrderTracesByJson("KDN", UUID.randomUUID().toString());
            //                //String result = api.getOrderTracesByJson("SF", "dddddd");
            //                System.out.print(result);
            //            }
            //String result = api.getOrderTracesByJson("DHL", "5376565950");
            String result = api.getOrderTracesByJson("", "");
            System.out.println(result);
            JSONObject parseObject = JSON.parseObject(result);
            String printTemplate = parseObject.getString("PrintTemplate");
            String string = parseObject.getString("SubPrintTemplates");
            List object = parseObject.getObject("SubPrintTemplates", List.class);
            //            for(Object s:object){
            //                
            //                System.out.println((String)s);  
            //            }

            System.out.println(printTemplate);
            /*    File file = new File("D:\\s\\"+"2SS00"+".pdf");
            FileOutputStream fos = new FileOutputStream( file );
            Decoder decoder = Base64.getDecoder();
            byte[] decode = decoder.decode(printTemplate);
            fos.write(decode);
            fos.close();
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //电商ID
    private String EBusinessID = "1279441";
    private String AppKey = "af5c859e-9741-452c-a908-140a25aba90f";

    //    private String EBusinessID = "1261585";
    //    private String AppKey = "16ca2a51-e690-4210-9a6e-3168849d63d9";

    //private String EBusinessID = "1348301";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    //private String AppKey = "02f9dfca-5465-4964-8c7c-e11676c4c1a4";

    //请求url
    //private String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";

//    private String ReqURL = "http://183.62.170.46:40030/openapi/gateway/exterfaceInvoke.json";
    private String ReqURL = "http://192.168.1.15:8080/openapi/gateway/exterfaceInvoke.json";

    //private String ReqURL="http://183.62.170.46:38093/openapi/gateway/exterfaceInvoke.json";
    //private String ReqURL = "http://10.1.1.31:8080/openapi/gateway/exterfaceInvoke.json";
    // private String ReqURL = "http://api.kdniao.cc/api/EOrderService";
    //private String ReqURL="http://120.25.79.131/api/apiservice";   
    //private String ReqURL="http://10.1.1.5:80/openapi/gateway/exterfaceInvoke.json";   

    /**
     * Json方式 查询订单物流轨迹
     * @throws Exception CustomerName
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception {
        //        String requestData =  "{\"CustomerName\":\"1111\",\"OrderCode\":\"1112\",\"ShipperCode\":\"YDGJ\",\"PayType\": 1,\"ExpType\": 1,\"Cost\":1.0,\"OtherCost\":1.0,\"Sender\":{\"PostCode\":\"\",\"Company\":\"LV\",\"Name\":\"Taylor\",\"Tel\":\"15018442396\",\"Mobile\":\"15018442396\",\"ProvinceName\":\"湖南省\",\"CityName\":\"长沙市\",\"ExpAreaName\":\"岳麓区\",\"Address\":\"湖南涉外经济学院\"},\"Receiver\":{\"Company\":\"GCCUI\",\"Name\":\"Yann\",\"Tel\":\"15018442396\",\"Mobile\":\"15018442396\",\"ProvinceName\":\"湖南省\",\"CityName\":\"长沙市\",\"ExpAreaName\":\"岳麓区\",\"Address\":\"长沙医学院\"},\"Commodity\": [{\"GoodsName\":\"手机\",\"Goodsquantity\":1,\"GoodsWeight\": 1.0}],\"Weight\": 1.0,\"Quantity\": 1,\"Volume\": 0.0,\"Remark\":\"小心轻放\",\"IsReturnPrintTemplate\":1}";
        //        String requestData =  "{\"CustomerName\":\"741612429\",\"AuthenticationKey\":\"hdDKe9lzHrn23dLz\",\"AuthenticationPassword\":\"qRaAAbs54wHoFpo28nprnIr5s\",\"ClientAccountNumber\":\"741612429\",\"ClientMeterNumber\":\"112624033\",\"OrderCode\":\"ls1423451A2s45d\",\"ShipperCode\":\"FEDEX_GJ\",\"PayType\":1,\"ExpType\": 1,\"Cost\":2.0,\"OtherCost\":1.0,\"Sender\":{\"PostCode\":\"518038\",\"Company\":\"LV\",\"Name\":\"lisi11\",\"Mobile\":\"13590292969\",\"Tel\":\"13590292969\",\"ProvinceName\":\"广东省\",\"CityName\":\"Shenzhen\",\"ExpAreaName\":\"futian\",\"Address\":\"hhhhh\"},\"Receiver\":{\"PostCode\":\"V7C4V4\",\"ProvinceCode\":\"BC\",\"CountryCode\":\"CA\",\"Company\":\"GCCUI\",\"Name\":\"Yann\",\"Mobile\":\"15018442396\",\"ProvinceName\":\"BC\",\"CityName\":\"Richmond\",\"ExpAreaName\":\"pudong\",\"Address\":\"AddressLine2\"},\"Commodity\": [{\"GoodsName\":\"手机\",\"Goodsquantity\":1,\"GoodsWeight\":0.5}],\"Weight\": 1.0,\"Quantity\":1,\"Volume\": 1.0,\"Remark\":\"测试\",\"IsReturnPrintTemplate\":1}";
        //        String requestData3 = "{\"IsReturnSignBill\":1,\"CustomerPwd\":\"741429\",\"SendSite\":\"741429\",\"Signwaybillcode\":\"741429\",\"Signwaybillcode\":\"741429\",\"CustomerName\":\"741612429\",\"AuthenticationKey\":\"hdDKe9lzHrn23dLz\",\"AuthenticationPassword\":\"qRaAAbs54wHoFpo28nprnIr5s\",\"ClientAccountNumber\":\"741612429\",\"ClientMeterNumber\":\"112624033\",\"OrderCode\":\"l2sttSS22s22245d\",\"ShipperCode\":\"LB\",\"PayType\":1,\"ExpType\": 1,\"Cost\":2.0,\"OtherCost\":1.0,\"Sender\":{\"PostCode\":\"518038\",\"Company\":\"LV\",\"Name\":\"lisi11\",\"Mobile\":\"13590292969\",\"Tel\":\"13590292969\",\"ProvinceName\":\"广东省\",\"CityName\":\"Shenzhen\",\"ExpAreaName\":\"futian\",\"Address\":\"hhhhh\"},\"Receiver\":{\"PostCode\":\"V7C4V4\",\"ProvinceCode\":\"BC\",\"CountryCode\":\"CA\",\"Company\":\"GCCUI\",\"Name\":\"Yann\",\"Mobile\":\"15018442396\",\"ProvinceName\":\"BC\",\"CityName\":\"Richmond\",\"ExpAreaName\":\"pudong\",\"Address\":\"AddressLine2\"},\"Commodity\": [{\"GoodsName\":\"手机\",\"Goodsquantity\":1,\"GoodsWeight\":0.5}],\"Weight\": 1.0,\"Quantity\":1,\"Volume\": 1.0,\"Remark\":\"测试\",\"IsReturnPrintTemplate\":1}";
        //        String requestData4 = "{\"IsReturnSignBill\":1,\"CustomerPwd\":\"741429\",\"SendSite\":\"741429\",\"Signwaybillcode\":\"741429\",\"Signwaybillcode\":\"741429\",\"CustomerName\":\"741612429\",\"AuthenticationKey\":\"hdDKe9lzHrn23dLz\",\"AuthenticationPassword\":\"qRaAAbs54wHoFpo28nprnIr5s\",\"ClientAccountNumber\":\"741612429\",\"ClientMeterNumber\":\"112624033\",\"OrderCode\":\"l2sttSS2sDDS2D24245d\",\"ShipperCode\":\"DBL\",\"PayType\":3,\"ExpType\": 1,\"Cost\":2.0,\"OtherCost\":1.0,\"Sender\":{\"PostCode\":\"518038\",\"Company\":\"LV\",\"Name\":\"lisi11\",\"Mobile\":\"13590292969\",\"Tel\":\"13590292969\",\"ProvinceName\":\"广东省\",\"CityName\":\"Shenzhen\",\"ExpAreaName\":\"futian\",\"Address\":\"hhhhh\"},\"Receiver\":{\"PostCode\":\"V7C4V4\",\"ProvinceCode\":\"BC\",\"CountryCode\":\"CA\",\"Company\":\"GCCUI\",\"Name\":\"Yann\",\"Mobile\":\"15018442396\",\"ProvinceName\":\"BC\",\"CityName\":\"Richmond\",\"ExpAreaName\":\"pudong\",\"Address\":\"AddressLine2\"},\"Commodity\": [{\"GoodsName\":\"手机\",\"Goodsquantity\":1,\"GoodsWeight\":0.5}],\"Weight\": 1.0,\"Quantity\":1,\"Volume\": 1.0,\"Remark\":\"测试\",\"IsReturnPrintTemplate\":1}";
        //        String requestData1 = "{\"CustomerName\":\"510087100\",\"AuthenticationKey\":\"rzh3nDDoPfeb5pfj\",\"AuthenticationPassword\":\"jqUvVhCpdJD6Je6XTSJbRmkRe\",\"ClientAccountNumber\":\"510087100\",\"ClientMeterNumber\":\"119019657\",\"OrderCode\":\"ls1w22423451A2s45d\",\"ShipperCode\":\"FEDEX_GJ\",\"PayType\":1,\"ExpType\": 1,\"Cost\":2.0,\"OtherCost\":1.0,\"Sender\":{\"PostCode\":\"518038\",\"Company\":\"LV\",\"Name\":\"lisi11\",\"Mobile\":\"13590292969\",\"Tel\":\"13590292969\",\"ProvinceName\":\"广东省\",\"CityName\":\"Shenzhen\",\"ExpAreaName\":\"futian\",\"Address\":\"hhhhh\"},\"Receiver\":{\"PostCode\":\"V7C4V4\",\"ProvinceCode\":\"BC\",\"CountryCode\":\"CA\",\"Company\":\"GCCUI\",\"Name\":\"Yann\",\"Mobile\":\"15018442396\",\"ProvinceName\":\"BC\",\"CityName\":\"Richmond\",\"ExpAreaName\":\"pudong\",\"Address\":\"AddressLine2\"},\"Commodity\": [{\"GoodsName\":\"手机\",\"Goodsquantity\":1,\"GoodsWeight\":0.5}],\"Weight\": 1.0,\"Quantity\":1,\"Volume\": 1.0,\"Remark\":\"测试\",\"IsReturnPrintTemplate\":1}";
        //        String requestData =  "{\"CustomerPwd\":\"4B00E72C00BE7A4514F9BD5255B31C94\",\"CustomerName\":\"5abb7c0d\",\"AuthenticationKey\":\"rzh3nDDoPfeb5pfj\",\"AuthenticationPassword\":\"jqUvVhCpdJD6Je6XTSJbRmkRe\",\"ClientAccountNumber\":\"510087100\",\"ClientMeterNumber\":\"119019657\",\"OrderCode\":\"ls1w22423451A2s45d\",\"ShipperCode\":\"PJ\",\"PayType\":1,\"ExpType\": 1,\"Cost\":2.0,\"OtherCost\":1.0,\"Sender\":{\"PostCode\":\"\",\"Company\":\"LV\",\"Name\":\"李四\",\"Mobile\":\"13590292969\",\"Tel\":\"13590292969\",\"ProvinceName\":\"广东省\",\"CityName\":\"广州市\",\"ExpAreaName\":\"荔湾区\",\"Address\":\"hhhhh\"},\"Receiver\":{\"PostCode\":\"\",\"ProvinceCode\":\"BC\",\"CountryCode\":\"CA\",\"Company\":\"GCCUI\",\"Name\":\"Yann\",\"Mobile\":\"15018442396\",\"ProvinceName\":\"广东省\",\"CityName\":\"广州市\",\"ExpAreaName\":\"天河区\",\"Address\":\"龙口中路2号\"},\"Commodity\": [{\"GoodsName\":\"手机\",\"Goodsquantity\":1,\"GoodsWeight\":0.5}],\"Weight\": 1.0,\"Quantity\":1,\"Volume\": 1.0,\"Remark\":\"\",\"IsReturnPrintTemplate\":1}";
//         String requestData = " {\"AuthenticationKey\":\"rzh3nDDoPfeb5pfj\",\"AuthenticationPassword\":\"jqUvVhCpdJD6Je6XTSJbRmkRe\",\"ClientAccountNumber\":\"510087100\",\"ClientMeterNumber\":\"119019657\",\"TemplateType\":1,\"ShipperCode\":\"YDGJ\",\"OrderCode\":\"DHL20171025004\",\"PayType\":\"3\",\"CustomerName\":\"prasanta\",\"CustomerPwd\":\"prasanta\",\"SendAccount\":\"603928923\",\"MonthCode\":\"603928923\",\"WeightUnit\":\"K\",\"DimensionUnit\":\"C\",\"CurrencyCode\":\"USD\",\"ProductCode\":\"P\",\"PackageType\":\"EE\",\"Dutiable\":{\"DeclaredValue\":100.21},\"TemplateSize\":\"8X4_PDF\",\"Sender\":{\"Telex\":\"Telex11\",\"FaxNumber\":\"FaxNumber\",\"Company\":\"Kuai Jin Data China\",\"Name\":\"Mr Wang\",\"Email\":\"em\",\"Tel\":\"854230478\",\"TelExtension\":\"2305\",\"Mobile\":\"15018442396\",\"PostCode\":\"101101\",\"CityName\":\"BEIJING\",\"CountryCode\":\"CN\",\"CountryName\":\"CHINA\",\"Address\":\"Kuai Jin Data China\",\"Address2\":\"Unit 5 Beddington Cross\",\"Address3\":\"Beddington Farm Road\"},\"Receiver\":{\"Telex\":\"Telex\",\"FaxNumber\":\"FaxNumber\",\"Email\":\"em\",\"Company\":\"IBM Bruse Pte Ltd\",\"Name\":\"Li Xi\",\"Tel\":\"15356456364\",\"TelExtension\":\"2305\",\"Mobile\":\"18826732223\",\"CityName\":\"MUNICH\",\"CountryCode\":\"DE\",\"CountryName\":\"GERMANY\",\"Address\":\"9 Business Park Central 1\",\"Address2\":\"3th Floor\",\"Address3\":\"The IBM Place\"},\"Commodity\":[{\"GoodsName\":\"IBM Computer\",\"GoodsPrice\":100.21,\"GoodsCode\":\"HS5210313\",\"GoodsCodeDesc\":\"Height Price\",\"GoodsWeight\":1,\"GoodsDimWeight\":2,\"GoodsDepth\":10,\"GoodsHeight\":10,\"GoodsWidth\":10}],\"AddServices\":[{\"Name\":\"II\",\"Value\":\"100.21\"}],\"IsReturnPrintTemplate\":1}";
        String OrderCode = System.currentTimeMillis() + "";
        String requestData = "{\n" +
                "    \"TemplateType\": 1,\n" +
                "    \"ShipperCode\": \"UBI\",\n" +
                "    \"OrderCode\": \"" + OrderCode + "\",\n" +
                "    \"PayType\": \"1\",\n" +
                "    \"CustomerName\": \"1612140001,360588EE1A5F2720B58C50DF9B3AAE58\",\n" +
                "    \"CustomerPwd\": \"fjldt\",\n" +
                "    \"SendAccount\": \"603928923\",\n" +
                "    \"SendSite\": \"123456\",\n" +
                "    \"MonthCode\": \"603928923\",\n" +
                "    \"WeightUnit\": \"K\",\n" +
                "    \"DimensionUnit\": \"C\",\n" +
                "    \"CurrencyCode\": \"USD\",\n" +
                "    \"ProductCode\": \"P\",\n" +
                "    \"PackageType\": \"EE\",\n" +
                "    \"Dutiable\": {\n" +
                "        \"DeclaredValue\": 100.21\n" +
                "    },\n" +
                "    \"TemplateSize\": \"180\",\n" +
                "    \"Sender\": {\n" +
                "        \"Company\": \"Kuai Jin Data China\",\n" +
                "        \"Name\": \"Mr Wang\",\n" +
                "        \"Email\": \"em\",\n" +
                "        \"Tel\": \"854230478\",\n" +
                "        \"TelExtension\": \"2305\",\n" +
                "        \"Mobile\": \"15018442396\",\n" +
                "        \"PostCode\": \"101101\",\n" +
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
                "            \"GoodsCode\": \"0099\",\n" +
                "            \"GoodsCodeDesc\": \"葛-瑞-格\",\n" +
                "            \"GoodsWeight\": 10,\n" +
                "            \"GoodsDimWeight\": 2,\n" +
                "            \"GoodsDepth\": 10,\n" +
                "            \"GoodsHeight\": 10,\n" +
                "            \"GoodsWidth\": 10\n" +
                "        },\n" +
                "        {\n" +
                "            \"GoodsName\": \"IBM Computer\",\n" +
                "            \"GoodsPrice\": 100.21,\n" +
                "            \"GoodsCode\": \"HS5210313\",\n" +
                "            \"GoodsCodeDesc\": \"Height Price\",\n" +
                "            \"GoodsWeight\": 1,\n" +
                "            \"GoodsDimWeight\": 2,\n" +
                "            \"GoodsDepth\": 10,\n" +
                "            \"GoodsHeight\": 10,\n" +
                "            \"GoodsWidth\": 10\n" +
                "        }\n" +
                "    ],\n" +
                "    \"AddServices\": [\n" +
                "        {\n" +
                "            \"Name\": \"II\",\n" +
                "            \"Value\": \"100.21\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"IsReturnPrintTemplate\": 1\n" +
                "}";

        System.out.println(requestData);
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1307");
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