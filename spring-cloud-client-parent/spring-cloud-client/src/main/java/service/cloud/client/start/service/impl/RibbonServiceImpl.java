package service.cloud.client.start.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.cloud.client.start.service.InterfaceRibbonService;

@Service
public class RibbonServiceImpl implements InterfaceRibbonService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String zipkinMethod(String name) {
        System.out.println("client zipkin");
        name = null == name ? "ay2626" : name;
        return restTemplate.getForObject("http://localhost:8091/zipkin2?name=" + name, String.class);
    }

    // 各种请求怎么写 里面的get方法啥意思 post get delete put ?????????
    @Override
    public String doServiceRequestHi(String name) {
        return restTemplate.getForObject("http://spring-cloud-client02/hi?name=" + name, String.class);
    }

    /**
     * 添加熔断操作
     *
     * @param success
     * @return
     */
    @Override
    @HystrixCommand
    public String doServiceRequestHalfFailed(Boolean success) {
        return restTemplate.getForObject("http://spring-cloud-client02/half?success=" + success, String.class);
    }

    @Override
    @HystrixCommand(fallbackMethod = "onError")
    public String doServiceRequestHalfFailed2(Boolean success) {
        return restTemplate.getForObject("http://spring-cloud-client02/half?success=" + success, String.class);
    }

    private String onError(Boolean success) {
        return "ERROR...";
    }
}
