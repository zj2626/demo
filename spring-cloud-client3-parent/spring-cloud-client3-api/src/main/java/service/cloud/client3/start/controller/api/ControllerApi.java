package service.cloud.client3.start.controller.api;

import org.springframework.web.bind.annotation.GetMapping;

public interface ControllerApi {

    @GetMapping("/api/fun1")
    public String fun(String name);
}
