package hello.control;

import hello.service.util.Change;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class GetSomething2 implements MessageListener<String, byte[]> {
    private static final Logger logger = LoggerFactory.getLogger(GetSomething2.class);
    private static final Logger logger2 = LoggerFactory.getLogger("sm.test");
    private static final Logger logger3 = LoggerFactory.getLogger("sm.err");
    private static final Logger logger4 = LoggerFactory.getLogger("sm.web");

    @Override
    public void onMessage(ConsumerRecord<String, byte[]> data) {
        logger.info("\n[第二个消费方法] 消息开始消费 " +
                "\nthread name:[" + Thread.currentThread().getName() + "] " +
                "\ntopic      :[" + data.topic() + "]" +
                "\npartition  :[" + data.partition() + "]" +
                "\nprev offset:[" + data.offset() + "]" +
                "");
        logger.info("收到消息: " + Change.byteArrayToStr(data.value()));
        logger.info("[第二个消费方法] 消息消费完成");

        /*测试logback*/

//        long len = 1024;
//        for (int i = 0; i < len; i++) {
//            logger.error("m" + i);
//        }

        logger.error("[第二个消费方法] 这是一个错误信息");

        /*level="INFO"*/
        logger2.debug("[第二个消费方法] sm.test");
        logger2.info("[第二个消费方法] sm.test");
        logger2.error("[第二个消费方法] sm.test");

        /*level="ERROR"*/
        logger3.debug("[第二个消费方法] sm.err"); // 日志级别是ERROR 所以info信息保存
        logger3.info("[第二个消费方法] sm.err"); // 日志级别是ERROR 所以info信息保存
        logger3.error("[第二个消费方法] sm.err");

        /*level="DEBUG"*/
        logger4.debug("[第二个消费方法] sm.web");
        logger4.info("[第二个消费方法] sm.web");
        logger4.error("[第二个消费方法] sm.web");
    }
}
