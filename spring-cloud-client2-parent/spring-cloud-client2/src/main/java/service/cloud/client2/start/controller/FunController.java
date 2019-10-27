package service.cloud.client2.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.cloud.client2.start.controller.api.ControllerApi;

// 不重启更新配置信息需要用到消息中间件
@RefreshScope // spring cloud bus配置自动刷新需要在包含更新的@Value的类加上 @RefreshScope
@RestController
public class FunController implements ControllerApi {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    String port;

    @Value("${spring.application.name}")
    String applicationName;

    @Override
    public String fun(@RequestParam String name) {
        return "hi fun " + name;
    }

    @Override
    public String fun2(@RequestParam Boolean success) {
        return "hi fun2 " + success;
    }

    @Override
    public String hi(String name) {
        return "hi " + name + ", "
                + ", get config from git success? "
                + ", it is : sprint-cloud-client-02";
    }

    @Override
    public String doHalfFailed(Boolean success) {
        System.out.println("Here..... " + success);
        if (!success) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "fucking sorry doo 2";
    }

    @Override
    public String zipkinMethod2(String name) {
        System.out.println("client2 zipkin");
        name = null == name ? "ay2626" : name;
        return restTemplate.getForObject("http://localhost:8090/zipkin3?name=" + name, String.class);
    }

    @Override
    public String zipkinMethod4(String name) {
        System.out.println("client2 zipkin4");
        //        return restTemplate.getForObject("http://localhost:18093/server/hi?name=" + name, String.class);
        return "success";
    }
}

