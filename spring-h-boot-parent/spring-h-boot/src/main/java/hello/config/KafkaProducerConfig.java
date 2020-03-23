package hello.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    /*****************************************************************************/
    /*********************************配置的第一个生产者****************************/
    /*****************************************************************************/

    /**
     * 并发数10
     *
     * @return
     */
    @Bean
    public KafkaTemplate<String, byte[]> kafkaTemplate(ProducerFactory<String, byte[]> kafkaProducerFactory) {
        System.out.println("********************************配置的第一个生产者***************************");
        KafkaTemplate<String, byte[]> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        kafkaTemplate.setDefaultTopic(defaultTopic);
        return kafkaTemplate;
    }
}
