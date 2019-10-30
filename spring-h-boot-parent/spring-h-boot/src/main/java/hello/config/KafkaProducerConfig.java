package hello.config;

import hello.bean.KafkaProducerBeanConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Autowired
    private KafkaProducerBeanConfig producerBeanConfig;

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
    public KafkaTemplate<String, byte[]> kafkaTemplate() {
        System.out.println("********************************配置的第一个生产者***************************");
        KafkaTemplate<String, byte[]> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory());
        kafkaTemplate.setDefaultTopic(defaultTopic);
        return kafkaTemplate;
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(6);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBeanConfig.getBootstrapServers());
//        props.put(ProducerConfig.RETRIES, producerBeanConfig.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producerBeanConfig.getBatchSize());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producerBeanConfig.getBufferMemory());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerBeanConfig.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerBeanConfig.getValueSerializer());
        return props;
    }

    private DefaultKafkaProducerFactory<String, byte[]> kafkaProducerFactory() {
        DefaultKafkaProducerFactory<String, byte[]> factory = new DefaultKafkaProducerFactory<>(producerConfigs());
        return factory;
    }
}
