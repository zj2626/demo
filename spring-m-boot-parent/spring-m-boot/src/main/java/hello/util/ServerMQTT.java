package hello.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerMQTT {
    
    @Bean
    public MQTTUtil init() throws Exception {
        System.out.println("ServerMQTT");
        MQTTUtil server = new MQTTUtil();
        
        server.connect(new PushCallback());
        
        return server;
    }
}
