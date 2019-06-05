/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.common.biz.service.impl.company;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Kdniao1008OrderAPI {

    //DEMO
    public static void main(String[] args) throws IOException {
        File file = new File("H://客户运单.xls");
        String[][] resultData = getData(file, 1);
        int rowLength = resultData.length;
//        for (int i = 0; i < rowLength; i++) {
//            for (int j = 0; j < resultData[i].length; j++) {
//                System.out.print(resultData[i][j] + "\t\t");
//            }
//            System.out.println();
//        }

        Kdniao1008OrderAPI api = new Kdniao1008OrderAPI();
        try {
            for (int i = 0; i < rowLength; i++) {
                System.out.print(resultData[i][0] + ' ');
                String result = api.getOrderTracesByJson("YTO", resultData[i][0]);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String EBusinessID = "1420123";
    private String AppKey = "99a85b59-87db-4bd2-b122-4e85b23a4240";

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
                "\t\t\"Name\": \"1255760\",\n" +
                "\t\t\"Tel\": \"\",\n" +
                "\t\t\"Mobile\": \"13700000000\",\n" +
                "\t\t\"ProvinceName\": \"广东省\",\n" +
                "\t\t\"CityName\": \"深圳市\",\n" +
                "\t\t\"ExpAreaName\": \" 龙岗区 \",\n" +
                "\t\t\"Address\": \"平湖街道平龙西路73号305\"\n" +
                "\t},\n" +
                "\t\"Receiver\": {\n" +
                "\t\t\"Name\": \"1255760\",\n" +
                "\t\t\"Tel\": \"\",\n" +
                "\t\t\"Mobile\": \"13800000000\",\n" +
                "\t\t\"ProvinceName\": \"河北\",\n" +
                "\t\t\"CityName\": \"廊坊市\",\n" +
                "\t\t\"ExpAreaName\": \"经开区\",\n" +
                "\t\t\"Address\": \"测试地址2\"\n" +
                "\t},\n" +
                "\t\"Commodity\": [{\n" +
                "\t\t\"GoodsName\": \"书本\"\n" +
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

    /**
     * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
     *
     * @param file       读取数据的源Excel
     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
     * @return 读出的Excel中数据的内容
     * @throws FileNotFoundException
     * @throws IOException
     */

    public static String[][] getData(File file, int ignoreRows)

            throws FileNotFoundException, IOException {

        List<String[]> result = new ArrayList<String[]>();

        int rowSize = 0;

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(

                file));

        // 打开HSSFWorkbook

        HSSFWorkbook wb;
        try (POIFSFileSystem fs = new POIFSFileSystem(in)) {

            wb = new HSSFWorkbook(fs);
        }

        HSSFCell cell = null;

        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

            HSSFSheet st = wb.getSheetAt(sheetIndex);

            // 第一行为标题，不取

            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

                HSSFRow row = st.getRow(rowIndex);

                if (row == null) {

                    continue;

                }

                int tempRowSize = row.getLastCellNum() + 1;

                if (tempRowSize > rowSize) {

                    rowSize = tempRowSize;

                }

                String[] values = new String[rowSize];

                Arrays.fill(values, "");

                boolean hasValue = false;

                for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {

                    String value = "";

                    cell = row.getCell(columnIndex);

                    if (cell != null) {

                        // 注意：一定要设成这个，否则可能会出现乱码
                        switch (cell.getCellType()) {

                            case HSSFCell.CELL_TYPE_STRING:

                                value = cell.getStringCellValue();

                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:

                                if (HSSFDateUtil.isCellDateFormatted(cell)) {

                                    Date date = cell.getDateCellValue();

                                    if (date != null) {

                                        value = new SimpleDateFormat("yyyy-MM-dd")

                                                .format(date);

                                    } else {

                                        value = "";

                                    }

                                } else {

                                    value = new DecimalFormat("0").format(cell

                                            .getNumericCellValue());

                                }

                                break;

                            case HSSFCell.CELL_TYPE_FORMULA:

                                // 导入时如果为公式生成的数据则无值

                                if (!cell.getStringCellValue().equals("")) {

                                    value = cell.getStringCellValue();

                                } else {

                                    value = cell.getNumericCellValue() + "";

                                }

                                break;

                            case HSSFCell.CELL_TYPE_BLANK:

                                break;

                            case HSSFCell.CELL_TYPE_ERROR:

                                value = "";

                                break;

                            case HSSFCell.CELL_TYPE_BOOLEAN:

                                value = (cell.getBooleanCellValue() == true ? "Y"

                                        : "N");

                                break;

                            default:

                                value = "";

                        }

                    }

                    if (columnIndex == 0 && value.trim().equals("")) {

                        break;

                    }

                    values[columnIndex] = rightTrim(value);

                    hasValue = true;

                }


                if (hasValue) {

                    result.add(values);

                }

            }

        }

        in.close();

        String[][] returnArray = new String[result.size()][rowSize];

        for (int i = 0; i < returnArray.length; i++) {

            returnArray[i] = (String[]) result.get(i);

        }

        return returnArray;

    }


    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */

    public static String rightTrim(String str) {

        if (str == null) {

            return "";

        }

        int length = str.length();

        for (int i = length - 1; i >= 0; i--) {

            if (str.charAt(i) != 0x20) {

                break;

            }

            length--;

        }

        return str.substring(0, length);

    }
}