package service.cloud.feign.start.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring-cloud-client01", fallback = SchedualServiceHiHystric.class)
public interface InterfaceHelloService {

    @GetMapping("/hi")
    String doServiceRequestHi(@RequestParam(value = "name", required = false) String name);

    @GetMapping("/half")
        // 没有@RequestParam就变POST了 ????? 为啥
    String doServiceRequestHalfFailed(@RequestParam Boolean success);
}
