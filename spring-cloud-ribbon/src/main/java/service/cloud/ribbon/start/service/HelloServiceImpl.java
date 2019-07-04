package service.cloud.ribbon.start.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.cloud.ribbon.start.service.impl.InterfaceHelloService;

@Service
public class HelloServiceImpl implements InterfaceHelloService {
    @Autowired
    private RestTemplate restTemplate;

    // 各种请求怎么写 里面的get方法啥意思 post get delete put ?????????
    @Override
    public String doServiceRequestHi(String name) {
        return restTemplate.getForObject("http://" + "spring-cloud-client01/hi?name=" + name, String.class);
    }

    /**
     * 添加熔断操作
     * @param success
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "onError")
    public String doServiceRequestHalfFailed(Boolean success) {
        return restTemplate.getForObject("http://" + "spring-cloud-client01/half?success=" + success, String.class);
    }

    private String onError(Boolean success){
        return "ERROR...";
    }
}
