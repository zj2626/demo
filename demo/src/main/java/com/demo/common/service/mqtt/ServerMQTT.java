package com.demo.common.service.mqtt;

import com.demo.common.service.mqtt.callback.PushCallback;
import org.junit.Test;

public class ServerMQTT {
    
    @Test
    public void demo() throws Exception {
        MQTTUtil server = new MQTTUtil("server-001");
        
        server.connect(new PushCallback());
        server.publish( "demo_topic_zj_2612", "这是推送消息的内容21");
    }
}
