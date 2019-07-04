package service.cloud.ribbon.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.cloud.ribbon.start.service.HelloService;

@RestController
public class HelloController {
    @Autowired
    private HelloService service;


    @GetMapping("/hi")
    public String home(String name) {
        name = null == name ? "zj2626" : name;
        System.out.println("###################");
        System.out.println(name);
        return service.doServiceRequest(name);
    }

}
