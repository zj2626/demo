package hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttInitConfig {
    
    @Bean
    public MqttUtil init() throws Exception {
        System.out.println("ServerMQTT begin");

        String name = "server-01";
        MqttUtil server = new MqttUtil(name);
        server.connect(new MyMqttCallback(name));

        System.out.println("ServerMQTT " + server);
        return server;
    }
}
