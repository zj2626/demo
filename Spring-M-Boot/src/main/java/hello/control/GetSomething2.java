package hello.control;

import hello.service.util.Change;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class GetSomething2 {
    private static final Logger logger = LoggerFactory.getLogger(GetSomething2.class);

    @KafkaListener(topics = {"kfk-to-topic-zj", "kfk-to-topic-zj-05"}, containerFactory = "kafkaBatchListener2")
    public void onMessage(ConsumerRecord<String, byte[]> data) {
        logger.info("[第二个消费方法] 消息开始消费 " +
                "\t[" + Thread.currentThread().getName() + "] " +
                "\t[" + data.topic() + "]" +
                "\t[" + data.offset() + "]" +
                "\t[" + data.partition() + "]" +
                "");
        logger.info(" [第二个消费方法] 收到消息: " + Change.byteArrayToStr(data.value()));
    }
}
