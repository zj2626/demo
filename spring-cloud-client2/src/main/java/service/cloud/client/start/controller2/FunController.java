package service.cloud.client.start.controller2;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunController implements ControllerApi {
    @Override
    public String fun(@RequestParam String name) {
        return "hi fun " + name;
    }
    
    @Override
    public String fun2(@RequestParam Boolean success) {
        return "hi fun2 " + success;
    }
}
