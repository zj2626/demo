package hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MqttInitConfig {
    String[] topic = {"demo_topic_2626"};
    int[] qos = {1};

    @Bean
    public MqttUtil init() throws Exception {
        System.out.println("ClientMQTT-01");

        String name = "client-01";
        MqttUtil server = new MqttUtil(name);
        server.connect(new MyMqttCallback(name));
        server.subscribe(topic, qos);

        System.out.println("ClientMQTT-01 " + server);
        return server;
    }

    @Bean
    public MqttUtil init2() throws Exception {
        System.out.println("ClientMQTT-02");

        String name2 = "client-02";
        MqttUtil server = new MqttUtil(name2);
        server.connect(new MyMqttCallback(name2));
        server.subscribe(topic, qos);

        System.out.println("ClientMQTT-02 " + server);
        return server;
    }
}
