package hello.control;

import hello.service.DoHSomething;
import hello.service.DoSendKafka;
import hello.service.model.KafkaRequest;
import hello.config.MqttUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoMQSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoMQSomething.class);

    @Autowired
    private MqttUtil mqtt;

    @Reference(group = "${dubbo.consumer.group}")
    private DoHSomething doHSomething;

    @Reference(group = "${dubbo.consumer.group}")
    private DoSendKafka doSendKafka;

    public boolean dokafka(KafkaRequest request) {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test kafka");

            String thing2 = doSendKafka.remoteToKafka(request);
            System.out.println("\n调用发消息结束 >>>> end " + thing2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void kafkaCustomProducer(String name) {
        doSendKafka.kafkaCustomProducer(name);
    }

    public boolean dorabbitmq() {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test rabbitmq");

            String thing2 = doHSomething.remoteToRabbitmq("abc");
            System.out.println("\n调用发消息结束 >>>> end " + thing2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean doactivemq() {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test activemq");

            String thing2 = doHSomething.remoteToActivemq("abc");
            System.out.println("\n调用发消息结束 >>>> end " + thing2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean domqtt(String topic, String message, int qos) {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test domqtt");

            mqtt.publish(topic, message, qos);
            System.out.println("\n调用发消息结束 >>>> end ");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
