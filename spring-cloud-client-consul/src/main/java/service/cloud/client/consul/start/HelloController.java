package service.cloud.client.consul.start;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hi")
    public String home(String name) {
        System.out.println("ribbon request");
        name = null == name ? "zj2626" : name;
        return name;
    }
}
