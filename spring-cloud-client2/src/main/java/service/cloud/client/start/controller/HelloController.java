package service.cloud.client.start.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

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
}
