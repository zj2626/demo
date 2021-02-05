package service.cloud.client2.start.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async("asyncThreadPool")
public class AsyncService {

    public String doDemo(String name) {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + " --- " + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }
}
