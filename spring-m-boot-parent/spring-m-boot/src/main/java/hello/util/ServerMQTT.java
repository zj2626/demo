package hello.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerMQTT {
    
    @Bean
    public void init() throws Exception {
        System.out.println("ServerMQTT");
        MQTTUtil server = new MQTTUtil();
        server.afterPropertiesSet();
        
        server.connect(new PushCallback());
        server.publish("demo_topic_zj_2612", "这是推送消息的内容21");
    }
}
