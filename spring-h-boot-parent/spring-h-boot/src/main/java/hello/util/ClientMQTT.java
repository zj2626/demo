package hello.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientMQTT {
    
    @Bean
    public MQTTUtil init() throws Exception {
        System.out.println("ClientMQTT");
        MQTTUtil server = new MQTTUtil();
        
        String[] topic = {"demo_topic_2626"};
        int[] qos = {1};
        
        server.connect(new SubscribeCallback());
        server.subscribe(topic, qos);
        
        return server;
    }
}
