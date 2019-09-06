package service.cloud.client.start.controller2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ControllerApi {
    @GetMapping("/fun1")
    public String fun(@RequestParam String name);
    
    @GetMapping("/fun2")
    public String fun2(@RequestParam Boolean success);
}
