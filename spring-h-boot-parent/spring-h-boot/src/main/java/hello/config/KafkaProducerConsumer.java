package hello.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaProducerConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConsumer.class);

    private static Properties producerProps = new Properties();

    @PostConstruct
    public void init(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::consume);
    }

    public void consume(){
        System.out.println("********************************配置的第二个生产者***************************");


    }
}
