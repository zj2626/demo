package hello.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.Arrays;

public class GetSomethingFromRabbit implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(GetSomethingFromRabbit.class);

    @Override
    public void onMessage(Message message) {
        logger.info("[Rabbitmq - - - 第一个消费方法] 消息开始消费 " +
                "\t[" + Thread.currentThread().getName() + "] " +
                "\t[" + Arrays.toString(message.getBody()) + "]" +
                "\t[" + message.getMessageProperties().toString() + "]" +
                "\t[" + message.hashCode() + "]" +
                "");
        logger.info(" [Rabbitmq - - - 第一个消费方法] 收到消息");
    }
}
