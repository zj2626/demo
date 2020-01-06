package com.demo.common.service.mqtt;

import com.demo.common.service.mqtt.callback.SubscribeCallback;
import org.junit.Test;

import java.util.Arrays;

public class ServerMQTT {
    
    
    @Test
    public void demo() throws Exception {
        MQTTUtil server = new MQTTUtil("client-001");
        
        String[] topic = {"demo_topic_zj_2612", "demo_topic_zj_2624"};
        // 至少一次
        int[] qos = {1, 1};
        
        server.connect(new SubscribeCallback());
        server.subscribe(topic, qos);

        System.out.println("已经订阅 " + Arrays.toString(topic));

        Thread.sleep(600000);
    }
}
