package service.cloud.feign.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.cloud.feign.start.service.InterfaceFunService;
import service.cloud.feign.start.service.InterfaceHelloService;

@RestController
public class HelloController {
    @Autowired
    private InterfaceHelloService helloService;

   @Autowired
    private InterfaceFunService funService;


    @GetMapping("/hi")
    public String home(String name) {
        System.out.println("feign request");
        name = null == name ? "ay2626" : name;
        return helloService.doServiceRequestHi(name);
    }
    
    @GetMapping("/fun")
    public String fun(String name) {
        System.out.println("feign request");
        name = null == name ? "ay2626" : name;
        return funService.fun(name);
    }

    @GetMapping("/test/hystrix")
    public String home(Boolean success) {
        System.out.println("feign request");
        success = null == success ? true : success;
        return helloService.doServiceRequestHalfFailed(success);
    }
}
