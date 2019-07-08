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

    @GetMapping("/zipkin")
    public String zipkinMethod(String name) {
        System.out.println("client zipkin");
        name = null == name ? "ay2626" : name;
        return restTemplate.getForObject("http://localhost:8091/zipkin2?name=" + name, String.class);
    }

    @GetMapping("/zipkin3")
    public String zipkinMethod3(String name) {
        System.out.println("client zipkin3");
        name = null == name ? "ay2626" : name;
        return restTemplate.getForObject("http://localhost:8091/zipkin4?name=" + name, String.class);
    }
}
