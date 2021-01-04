package service.cloud.client2.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.cloud.client2.start.controller.api.ControllerApi;
import service.cloud.client2.start.service.AsyncService;
import service.cloud.client2.start.service.ControllerFeignApi;

// 不重启更新配置信息需要用到消息中间件
@RefreshScope // spring cloud bus配置自动刷新需要在包含更新的@Value的类加上 @RefreshScope
@RestController
public class FunController implements ControllerApi {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private ControllerFeignApi feignApi;

    @Value("${server.port}")
    String port;

    @Value("${spring.application.name}")
    String applicationName;

    @Override
    public String fun(@RequestBody String name) {
        return "hi fun " + name;
    }

    @Override
    public String fun2(@RequestBody Boolean success) {
        return "hi fun2 " + success;
    }

    @Override
    public String hi(@RequestBody String name) {
        return "hi " + name + ", "
                + ", get config from git success? "
                + ", it is : sprint-cloud-client-02";
    }

    @Override
    public String doHalfFailed(@RequestBody Boolean success) {
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
    public String asyncMethod(@RequestBody String name) {
        System.out.println("asyncMethod");
        name = null == name ? "ay2626" : name;
        String result = asyncService.doDemo(name);
        System.out.println(result);
        return result;
    }

    @Override
    public String feignRequest(@RequestBody String name) {
        System.out.println("feignRequest");
        name = null == name ? "ay2626" : name;
        return feignApi.fun(name);
    }

    @Override
    public String zipkinMethod2(@RequestBody String name) {
        System.out.println("client2 zipkin");
        name = null == name ? "ay2626" : name;
        return restTemplate.getForObject("http://localhost:18080/api/products", String.class);
    }
}

