package service.cloud.ribbon.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/hi")
    public String home(String name) {
        name = null == name ? "zj2626" : name;
        System.out.println("###################");
        System.out.println(name);
        return restTemplate.getForObject("http://" + "spring-cloud-client01/hi?name=" + name, String.class);
    }

}
