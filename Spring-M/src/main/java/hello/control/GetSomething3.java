package hello.control;

import hello.service.util.Change;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class GetSomething3 implements MessageListener<String, byte[]> {

    @Override
    public void onMessage(ConsumerRecord<String, byte[]> data) {
        System.out.println("\n[第三个消费方法 中途新加的] 消息开始消费 " +
                "\nthread name:[" + Thread.currentThread().getName() + "] " +
                "\ntopic      :[" + data.topic() + "]" +
                "\npartition  :[" + data.partition() + "]" +
                "\nprev offset:[" + data.offset() + "]" +
                "");
        System.out.println("收到消息: " + Change.byteArrayToStr(data.value()));
        System.out.println("[第三个消费方法 中途新加的] 消息消费完成");
    }
}
