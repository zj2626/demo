package hello.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientMQTT {
    
    @Bean
    public MQTTUtil init() throws Exception {
        System.out.println("ClientMQTT");
    
        String[] topic = {"demo_topic_2626"};
        int[] qos = {1};
        
        String name = "client-02";
        MQTTUtil server = new MQTTUtil(name);
        server.connect(new SubscribeCallback(name));
        server.subscribe(topic, qos);
    
        String name2 = "client-02";
        MQTTUtil server2 = new MQTTUtil(name2);
        server2.connect(new SubscribeCallback(name2));
        server2.subscribe(topic, qos);
        
        return server;
    }
}
