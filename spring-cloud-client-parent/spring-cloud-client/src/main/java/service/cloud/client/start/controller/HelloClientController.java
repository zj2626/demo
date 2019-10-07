package service.cloud.client.start.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.cloud.client.start.service.InterfaceFeignService;
import service.cloud.client.start.service.InterfaceRibbonService;

// 不重启更新配置信息需要用到消息中间件
@RefreshScope // spring cloud bus配置自动刷新需要在包含更新的@Value的类加上 @RefreshScope
@RestController
public class HelloClientController {
    @Autowired
    private InterfaceRibbonService ribbonService;

    @Autowired
    private InterfaceFeignService feignService;

    @Value("${server.port}")
    String port;

    @Value("${spring.application.name}")
    String applicationName;

    @Value("${foo:#{null}}")
    String foo;

    @Value("${spring.profiles.active}")
    String active;

    // http://localhost:8090/hi?name=zj2626
    // http://localhost:8190/actuator/bus-refresh
    @GetMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi " + name + ", i am from port:" + port + ", active=" + active
                + ", get config from git success? " + (StringUtils.isNotEmpty(foo) ? "success" : "failed")
                + ", it is : " + foo;
    }

    @GetMapping("/half")
    public String half(@RequestParam Boolean success) {
        System.out.println("Here..... " + success);
        if (!success) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "hi " + success + ",i am from port:" + port;
    }

    @GetMapping("/ribbon/zipkin")
    public String zipkinMethod(String name) {
        name = null == name ? "ay2626" : name;
        return ribbonService.zipkinMethod(name);
    }

    @GetMapping("/ribbon/zipkin3")
    public String zipkinMethod3(String name) {
        name = null == name ? "ay2626" : name;
        return ribbonService.zipkinMethod3(name);
    }

    @GetMapping("/ribbon/requestHi")
    public String ribbonRequestHi(String name) {
        name = null == name ? "ay2626" : name;
        return ribbonService.doServiceRequestHi(name);
    }

    @GetMapping("/ribbon/requestHalfFailed")
    public String doServiceRequestHalfFailed(Boolean success) {
        success = null == success ? true : success;
        return ribbonService.doServiceRequestHalfFailed(success);
    }

    @GetMapping("/ribbon/requestHalfFailed2")
    public String requestHalfFailed2(Boolean success) {
        success = null == success ? true : success;
        return ribbonService.doServiceRequestHalfFailed2(success);
    }

    @GetMapping("/feign/fun")
    public String fun(String name) {
        System.out.println("feign request");
        name = null == name ? "ay2626" : name;
        return feignService.fun(name);
    }

    @GetMapping("/feign/requestHi")
    public String requestHi(String name) {
        name = null == name ? "ay2626" : name;
        return feignService.doServiceRequestHi(name);
    }

    @GetMapping("/feign/test/hystrix")
    public String home(Boolean success) {
        System.out.println("feign request");
        success = null == success ? true : success;
        return feignService.doServiceRequestHalfFailed(success);
    }
}
