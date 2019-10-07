package service.cloud.client.start.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ControllerApi {
    @GetMapping("/fun1")
    public String fun(@RequestParam String name);
    
    @GetMapping("/fun2")
    public String fun2(@RequestParam Boolean success);

    @GetMapping("/hi")
    String doServiceRequestHi(@RequestParam(value = "name", required = false) String name);

    @GetMapping("/half")
// 没有@RequestParam就变POST了 ????? 为啥
    String doServiceRequestHalfFailed(@RequestParam Boolean success);
}
