package hello.service.impl;

import hello.service.DoHSomething;
import hello.service.util.Change;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service("doHSomethingImpl")
public class DoHSomethingImpl implements DoHSomething {
    private String topicName = "kfk-to-topic-zj-05";

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public DoHSomethingImpl() {
        System.out.println("构造造 DoHSomethingImpl");
    }

    @Override
    public String sayHello(String name) {
        return ">Dubbo " + name;
    }

    @Override
    public String sayFuckToKafka(String name) {
        String result = sendMethod(name);
        System.out.println("发送kafka消息完毕 " + result);
        return result;
    }


    private String sendMethod(String name) {
        System.out.println("KAFKA -> " + (null != kafkaTemplate));
        if (null != kafkaTemplate) {
            System.out.println(">>>");
            List<PartitionInfo> partitionInfos = kafkaTemplate.partitionsFor(topicName);
            System.out.println("topic[" + topicName + "]的partition: " + partitionInfos.size() + "\t" + partitionInfos);
            kafkaTemplate.send(topicName, Change.strToByteArray("this is first message " + UUID.randomUUID()));
            System.out.println(">>>>>>>>>");
        }

        return ">Dubbo fuck u " + name;
    }

    private String sendMethodByProducer(String name) {
        Properties props = new Properties();

        return ">Dubbo fuck u by producer " + name;
    }

    @Override
    public String sayShit(String name) {
        System.out.println("SHIT****************************** " + name);
        return "shit u " + name;
    }
}
