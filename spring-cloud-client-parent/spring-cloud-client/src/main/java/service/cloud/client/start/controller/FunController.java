package service.cloud.client.start.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.cloud.client.start.service.InterfaceFeignService;
import service.cloud.client.start.service.InterfaceRibbonService;
import service.cloud.client.start.controller.api.ControllerApi;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class FunController implements ControllerApi {
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
    @Override
    public String hi(@RequestParam String name) {
        return "hi " + name + ", i am from port:" + port + ", active=" + active
                + ", get config from git success? " + (StringUtils.isNotEmpty(foo) ? "success" : "failed")
                + ", it is : " + foo;
    }

    @Override
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

    @Override
    public String zipkinMethod(String name) {
        name = null == name ? "ay2626" : name;
        return ribbonService.zipkinMethod(name);
    }

    @Override
    public String ribbonRequestHi(String name) {
        name = null == name ? "ay2626" : name;
        return ribbonService.doServiceRequestHi(name);
    }

    @Override
    public String requestHalfFailed(Boolean success) {
        success = null == success ? true : success;
        return ribbonService.doServiceRequestHalfFailed(success);
    }

    @Override
    public String requestHalfFailed2(Boolean success) {
        success = null == success ? true : success;
        return ribbonService.doServiceRequestHalfFailed2(success);
    }

    @Override
    public String fun(String name) {
        System.out.println("feign request");
        name = null == name ? "ay2626" : name;
        return feignService.fun(name);
    }

    @Override
    public String requestHi(String name) {
        name = null == name ? "ay2626" : name;
        return feignService.hi(name);
    }

    @Override
    public String testHystrix(Boolean success) {
        System.out.println("feign request");
        success = null == success ? true : success;
        return feignService.doHalfFailed(success);
    }
}

