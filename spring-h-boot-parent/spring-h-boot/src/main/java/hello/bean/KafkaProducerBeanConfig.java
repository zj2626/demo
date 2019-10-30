package hello.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka.producer")
//@PropertySource(value = "application.properties")
public class KafkaProducerBeanConfig {
    private String bootstrapServers;
    private Integer retries;
    private Integer batchSize;
    private Integer bufferMemory;
    private String keySerializer;
    private String valueSerializer;
}
