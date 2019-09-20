package hello.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientMQTT {
    
    @Bean
    public void init() throws Exception {
        System.out.println("ClientMQTT");
        MQTTUtil server = new MQTTUtil();
        server.afterPropertiesSet();
        
        String[] topic = {"demo_topic_zj_2612"};
        int[] qos = {1};
        
        server.connect(new SubscribeCallback());
        server.subscribe(topic, qos);
    }
}
