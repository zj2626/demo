package service.cloud.client.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    String port;

    @Value("${spring.application.name}")
    String applicationName;

    @GetMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi " + name + ",i am from port:" + port;
    }

    @GetMapping("/actuator/info")
    public String actuator() {
        return "by " + applicationName + ", i am from port:" + port;
    }

    @GetMapping("/half")
    public String half(@RequestParam Boolean success) {
        return "hi " + success + ",i am from port:" + port;
    }

    @GetMapping("/zipkin2")
    public String zipkinMethod2(String name) {
        System.out.println("client2 zipkin");
        name = null == name ? "ay2626" : name;
        return restTemplate.getForObject("http://localhost:8090/zipkin3?name=" + name, String.class);
    }

    @GetMapping("/zipkin4")
    public String zipkinMethod4(String name) {
        System.out.println("client2 zipkin4");
//        return restTemplate.getForObject("http://localhost:18093/server/hi?name=" + name, String.class);
        return "success";
    }
}
