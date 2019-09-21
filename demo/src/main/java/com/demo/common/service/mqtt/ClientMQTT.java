package com.demo.common.service.mqtt;

import com.demo.common.service.mqtt.callback.SubscribeCallback;
import org.junit.Test;

public class ClientMQTT {
    
    
    @Test
    public void demo() throws Exception {
        MQTTUtil server = new MQTTUtil("client-001");
        
        String[] topic = {"demo_topic_zj_2612"};
        // 至少一次
        int[] qos = {1};
        
        server.connect(new SubscribeCallback());
        server.subscribe(topic, qos);
    
        Thread.sleep(300000);
    }
}
