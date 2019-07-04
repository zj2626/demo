package service.cloud.ribbon.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

    public String doServiceRequest(String name) {
        name = null == name ? "zj2626" : name;
        System.out.println("###################");
        System.out.println(name);
        return restTemplate.getForObject("http://" + "spring-cloud-client01/hi?name=" + name, String.class);
    }
}
