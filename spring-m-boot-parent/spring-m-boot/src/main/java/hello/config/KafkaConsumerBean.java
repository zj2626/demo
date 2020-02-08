package hello.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka.consumer")
//@PropertySource(value = "application.properties")
public class KafkaConsumerBean {

    private String bootstrapServers;

    private String groupId;

    private Boolean enableAutoCommit;

    private Integer autoCommitInterval;

    private String autoOffsetReset;

    private String keyDeserializer;

    private String valueDeserializer;

    private Integer maxPollRecords;

    private Integer sessionTimeout;
}
