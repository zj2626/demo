package hello.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
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
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Autowired
    private KafkaConsumerBean consumerBeanConfig;

    @Value("${spring.kafka.listener.concurrency}")
    private Integer concurrency;

    @Value("${kafka.consumer.group-id[g2]}")
    private String groupId2;

    /*****************************************************************************/
    /*********************************配置的第一个消费者****************************/
    /*****************************************************************************/

    /**
     * 并发数10
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory-001")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, byte[]>> kafkaBatchListener() {
        System.out.println("********************************配置的第一个消费者***************************");
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //批量消费
        //        factory.setBatchListener(batchListener);
        //如果消息队列中没有消息，等待timeout毫秒后，调用poll()方法。
        // 如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
        //手动提交无需配置
        //        factory.getContainerProperties().setPollTimeout(pollTimeout);
        //设置提交偏移量的方式， MANUAL_IMMEDIATE 表示消费一条提交一次；MANUAL表示批量提交一次
        //        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        factory.setConcurrency(concurrency);
        //        factory.setMessageConverter();
        return factory;
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>(11);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, consumerBeanConfig.getAutoCommitInterval());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBeanConfig.getBootstrapServers());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerBeanConfig.getEnableAutoCommit());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerBeanConfig.getMaxPollRecords());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerBeanConfig.getAutoOffsetReset());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerBeanConfig.getGroupId());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerBeanConfig.getSessionTimeout());
        //        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
        //        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerBeanConfig.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerBeanConfig.getValueDeserializer());
        return props;
    }

    private ConsumerFactory<String, byte[]> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /*****************************************************************************/
    /*********************************配置的第二个消费者****************************/
    /*****************************************************************************/

    /**
     * 并发数5
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "concurrentMessageListenerContainer-002")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, byte[]>> kafkaBatchListener2() {
        System.out.println("********************************配置的第二个消费者***************************");
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2());
        factory.setConcurrency(5);
        return factory;
    }

    private Map<String, Object> consumerConfigs2() {
        Map<String, Object> props = new HashMap<>(11);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, consumerBeanConfig.getAutoCommitInterval());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBeanConfig.getBootstrapServers());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerBeanConfig.getEnableAutoCommit());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerBeanConfig.getMaxPollRecords());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerBeanConfig.getAutoOffsetReset());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId2);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerBeanConfig.getSessionTimeout());
        //        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
        //        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerBeanConfig.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerBeanConfig.getValueDeserializer());
        return props;
    }

    private ConsumerFactory<String, byte[]> consumerFactory2() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs2());
    }
}
