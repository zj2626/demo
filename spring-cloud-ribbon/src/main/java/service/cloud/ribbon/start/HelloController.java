package service.cloud.ribbon.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.cloud.ribbon.start.service.impl.InterfaceHelloService;

@RestController
public class HelloController {
    @Autowired
    private InterfaceHelloService service;

    @GetMapping("/hi")
    public String home(String name) {
        System.out.println("ribbon request");
        name = null == name ? "zj2626" : name;
        return service.doServiceRequestHi(name);
    }

    @GetMapping("/test/hystrix")
    public String home(Boolean success) {
        System.out.println("ribbon request");
        success = null == success ? true : success;
        return service.doServiceRequestHalfFailed(success);
    }
}
