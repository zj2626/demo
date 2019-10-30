package hello.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka.producer")
//@PropertySource(value = "application.properties")
public class KafkaProducerConfig {
    private String bootstrapServers;
    private String retries;
    private String batchSize;
    private String bufferMemory;
    private String keySerializer;
    private String valueSerializer;
    private String defaultTopic;
}
