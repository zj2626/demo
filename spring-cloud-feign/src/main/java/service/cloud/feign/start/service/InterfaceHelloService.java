package service.cloud.feign.start.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("spring-cloud-client01")
public interface InterfaceHelloService {

    @GetMapping("/hi")
    String doServiceRequest(@RequestParam(value = "name", required = false) String name);
}
