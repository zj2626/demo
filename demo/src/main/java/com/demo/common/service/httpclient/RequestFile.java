package com.demo.common.service.httpclient;

import com.demo.common.service.httpclient.abs.Request;
import com.demo.common.service.httpclient.client.HttpClientDemo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestFile extends Request {
    @Test
    public void test() throws InterruptedException {
        clientDemo = new HttpClientDemo(this);
        httpClient = HttpClientDemo.httpClient;

        clientDemo.execute();
    }

    @Override
    public String doRequest(Map<String, String> parameter) throws Exception {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        // 第一个文件
        String filesKey = "files";
        File file1 = new File("E:\\p2537788190.jpg");
        multipartEntityBuilder.addBinaryBody(filesKey, file1);
        // 第二个文件(多个文件的话，使用同一个key就行，后端用数组或集合进行接收即可)
        File file2 = new File("E:\\mmexport1550242489729.jpg");
        // 防止服务端收到的文件名乱码。 我们这里可以先将文件名URLEncode，然后服务端拿到文件名时在URLDecode。就能避免乱码问题。
        // 文件名其实是放在请求头的Content-Disposition里面进行传输的，如其值为form-data; name="files"; filename="头像.jpg"
        multipartEntityBuilder.addBinaryBody(filesKey, file2, ContentType.DEFAULT_BINARY, URLEncoder.encode(file2.getName(), "utf-8"));

        // 其它参数(注:自定义contentType，设置UTF-8是为了防止服务端拿到的参数出现乱码)
        multipartEntityBuilder.addTextBody("id", "25", ContentType.create("text/plain", StandardCharsets.UTF_8));
        multipartEntityBuilder.addTextBody("name", "邓沙利文", ContentType.create("text/plain", StandardCharsets.UTF_8));

        // 2. 设置请求参数 拼接请求地址
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(18080)
                    .setPath("/api/files")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 3. 创建Post请求
        HttpPost httpPost = new HttpPost(uri);
        // 4. 不需要手动设置请求头信息
        //  clientDemo.makeMultipartHeader(httpPost);
        // 5. 设置请求体参数
        httpPost.setEntity(multipartEntityBuilder.build());

        // 设置响应模型
        CloseableHttpResponse httpResponse = null;
        // 执行请求
        try {
            System.out.println(httpPost.getURI());
            httpResponse = httpClient.execute(httpPost);
            // 从响应中获得数据
            if (null != httpResponse) {
                HttpEntity responseEntity = httpResponse.getEntity();
                if (200 == httpResponse.getStatusLine().getStatusCode()) {
                    // 响应数据字符串
                    return EntityUtils.toString(responseEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientDemo.closeClient(httpResponse);
        }
        return null;
    }
}
