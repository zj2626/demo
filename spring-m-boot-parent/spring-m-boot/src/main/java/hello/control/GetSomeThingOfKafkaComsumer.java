package hello.control;

import hello.service.model.Change;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class GetSomeThingOfKafkaComsumer {
    private static final Logger logger = LoggerFactory.getLogger(GetSomeThingOfKafkaComsumer.class);

    @KafkaListener(topics = {"${kafka.consumer.topic[0]}", "${kafka.consumer.topic[1]}", "${kafka.consumer.topic[2]}"}, containerFactory = "kafkaBatchListener")
    public void listen(ConsumerRecord<String, byte[]> data) {
        logger.info("[第一个消费方法] 消息开始消费 " +
                "\t[" + Thread.currentThread().getName() + "] " +
                "\t[" + data.topic() + "]" +
                "\t[" + data.offset() + "]" +
                "\t[" + data.partition() + "]" +
                "");
        logger.info(" [第一个消费方法] 收到消息: " + Change.byteArrayToStr(data.value()));
//        try {
//            Thread.sleep(999000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /*
     * 当thread有10个 partation有5个
     * thread    partation
     * 0         0
     * 1         2
     * 2         3
     * 3         4
     * 4
     * 5
     * 6
     * 7
     * 8
     * 9         1
     *
     *
     *  当thread有3个 partation有5个
     * thread    partation
     * 0         0, 1
     * 1         2, 3
     * 2         4
     *
     * */

    @KafkaListener(topics = {"${kafka.consumer.topic[0]}", "${kafka.consumer.topic[1]}"}, containerFactory = "kafkaBatchListener2")
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
