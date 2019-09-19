package com.demo.common.service.kafka;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaTest {
    @Test
    public void startProducter() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        try {
            KafkaTemplate kafkaTemplate = (KafkaTemplate) applicationContext.getBean("kafkaTemplate");
            for (int j = 0; j < 3; j++) {
                for (int i = 1; i < 5; i++) {
                    String msg = "msg-" + i + "这是测试消息： 来自于topic-" + i + "，此时j=" + j + "并且i=" + i + " ;";
                    //向topicOne发送消息
                    kafkaTemplate.send("topicOne", msg);
                    System.out.println("send done");

                    Thread.sleep(3000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void startConsummer() throws InterruptedException {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TimeUnit.HOURS.sleep(1);
//        Thread.sleep(1000000);
    }
}
