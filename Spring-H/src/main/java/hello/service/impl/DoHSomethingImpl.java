package hello.service.impl;

import hello.service.DoHSomething;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service("doHSomethingImpl")
public class DoHSomethingImpl implements DoHSomething {
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
        System.out.println("KAFKA -> " + (null != kafkaTemplate));
        if(null != kafkaTemplate){
            kafkaTemplate.send("kfk-topic-zj", "this is second message".getBytes());
        }

        return ">Dubbo fuck u " + name;
    }

    @Override
    public String sayShit(String name) {
        System.out.println("SHIT****************************** " + name);
        return "shit u " + name;
    }
}
