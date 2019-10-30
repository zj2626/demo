package service.cloud.client2.start.service;

import org.springframework.scheduling.annotation.Async;

@Async("asyncThreadPool")
public class AsyncService {

    public void method(){
        System.out.println("METHOD");
    }
}
