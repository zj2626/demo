package service.cloud.client.start.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.cloud.client.start.controller.api.ControllerApi;

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

    @Override
    public String doServiceRequestHi(String name) {
        return "fucking sorry doo";
    }

    @Override
    public String doServiceRequestHalfFailed(Boolean success) {
        System.out.println("Here..... " + success);
        if (!success) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "fucking sorry doo 2";
    }
}
