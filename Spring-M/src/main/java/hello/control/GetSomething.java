package hello.control;

import hello.service.util.Change;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class GetSomething implements MessageListener<String, byte[]> {
    private static final Logger logger = LoggerFactory.getLogger("remote.degggggg");

    @Override
    public void onMessage(ConsumerRecord<String, byte[]> data) {
        logger.info("\n[第一个消费方法] 消息开始消费 " +
                "\nthread name:[" + Thread.currentThread().getName() + "] " +
                "\ntopic      :[" + data.topic() + "]" +
                "\npartition  :[" + data.partition() + "]" +
                "\nprev offset:[" + data.offset() + "]" +
                "");
        logger.info("收到消息: " + Change.byteArrayToStr(data.value()));
//        try {
//            Thread.sleep(999000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        logger.info("[第一个消费方法] 消息消费完成 ");
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
}
