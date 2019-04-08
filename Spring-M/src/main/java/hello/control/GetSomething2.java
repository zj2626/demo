package hello.control;

import hello.service.util.Change;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class GetSomething2 implements MessageListener<String, byte[]> {
    private static final Logger logger = LoggerFactory.getLogger(GetSomething2.class);

    private TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void onMessage(ConsumerRecord<String, byte[]> data) {
        logger.info("[第二个消费方法] 消息开始消费 " +
                "\t[" + Thread.currentThread().getName() + "] " +
                "\t[" + data.topic() + "]" +
                "\t[" + data.offset() + "]" +
                "\t[" + data.partition() + "]" +
                "");
        logger.info(" [第二个消费方法] 收到消息: " + Change.byteArrayToStr(data.value()));

        System.out.println(transactionTemplate != null);
    }
}
