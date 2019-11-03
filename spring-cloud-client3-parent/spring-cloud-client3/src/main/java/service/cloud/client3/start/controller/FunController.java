package service.cloud.client3.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.cloud.client3.start.controller.api.ControllerApi;

// 不重启更新配置信息需要用到消息中间件
@RefreshScope // spring cloud bus配置自动刷新需要在包含更新的@Value的类加上 @RefreshScope
@RestController
public class FunController implements ControllerApi {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String fun(@RequestBody String name) {
        return "hi fun  this is client 3 by " + name;
    }
}

