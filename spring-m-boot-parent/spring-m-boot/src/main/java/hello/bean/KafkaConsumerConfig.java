package hello.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka.consumer")
//@PropertySource(value = "application.properties")
public class KafkaConsumerConfig {
    private String bootstrapServers;
    private String groupId;
    private String enableAutoCommit;
    private String autoCommitInterval;
    private String autoOffsetReset;
    private String keyDeserializer;
    private String valueDeserializer;
    private String maxPollRecords;
    private String sessionTimeout;
    private String concurrency;
}
