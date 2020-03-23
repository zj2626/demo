package hello.callback;

import com.alibaba.fastjson.JSON;
import hello.service.model.Change;
import hello.service.model.PushData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaComsumerDefaultConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaComsumerDefaultConfig.class);

    @KafkaListener(
            topics = {"#{'${kafka.consumer.topic}'.split(',')[1]}", "#{'${kafka.consumer.topic}'.split(',')[0]}"},
            containerFactory = "kafkaBatchListener")
    public void listen(ConsumerRecord<String, byte[]> data) {
        KafkaComsumerDefaultConfig.getRecordInfo("第一个消费方法", data);
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

    @KafkaListener(
            topics = {"#{'${kafka.consumer.topic}'.split(',')[2]}", "kfk-to-topic-zj-04"},
            containerFactory = "kafkaBatchListener2")
    public void onMessage(ConsumerRecord<String, byte[]> data) {
        KafkaComsumerDefaultConfig.getRecordInfo("第二个消费方法", data);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getRecordInfo(String msg, ConsumerRecord<String, byte[]> data) {
        logger.info("[" + msg + "] 消息开始消费 " +
                "\nTopic        [" + data.topic() + "]" +
                "\n消费者offset  [" + data.offset() + "]" +
                "\npartition    [" + data.partition() + "]" +
                "\nKey          [" + data.key() + "] " +
                "\n线程名称      [" + Thread.currentThread().getName() + "] " +
                "\n收到消息      [" + JSON.parseObject(Change.byteArrayToStr(data.value()), PushData.class) + "]" +
                "\n");
    }
}
