package service.cloud.client2.start.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.cloud.client2.start.controller.api.ControllerApi;

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
    public String hi(String name) {
        return "hi " + name + ", "
                + ", get config from git success? "
                + ", it is : sprint-cloud-client-02";
    }

    @Override
    public String doHalfFailed(Boolean success) {
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
