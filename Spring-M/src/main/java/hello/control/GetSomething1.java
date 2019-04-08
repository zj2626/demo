package hello.control;

import hello.service.util.Change;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class GetSomething1 implements MessageListener<String, byte[]> {
    private static final Logger logger = LoggerFactory.getLogger(GetSomething1.class);

    private TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void onMessage(ConsumerRecord<String, byte[]> data) {
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

        System.out.println(transactionTemplate != null);
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
