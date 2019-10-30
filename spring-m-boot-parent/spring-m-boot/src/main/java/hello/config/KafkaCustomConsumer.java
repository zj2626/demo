package hello.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaCustomConsumer{

    @PostConstruct
    public void init(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::consume);
    }

    public void consume(){
        System.out.println("********************************配置的第三个消费者***************************");


    }
}
