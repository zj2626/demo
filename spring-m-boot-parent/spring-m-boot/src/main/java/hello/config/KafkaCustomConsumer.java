package hello.config;

import com.alibaba.fastjson.JSON;
import hello.bean.KafkaConsumerBeanConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaCustomConsumer<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaCustomConsumer.class);

    @Autowired
    private KafkaConsumerBeanConfig consumerBeanConfig;

    @Value("${kafka.consumer.topic[0]}")
    private Integer topic;

    @Value("${spring.kafka.listener.concurrency}")
    private Integer concurrency;

    @Value("${kafka.consumer.group-id[g3]}")
    private String groupId3;

    private static Properties consumerProps = new Properties();

    private void makeConsumerConfig() {
        consumerProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, consumerBeanConfig.getAutoCommitInterval());
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBeanConfig.getBootstrapServers());
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerBeanConfig.getEnableAutoCommit());
        consumerProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerBeanConfig.getMaxPollRecords());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerBeanConfig.getAutoOffsetReset());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerBeanConfig.getGroupId());
        consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerBeanConfig.getSessionTimeout());
        //        consumerProps.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
        //        consumerProps.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerBeanConfig.getKeyDeserializer());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerBeanConfig.getValueDeserializer());
    }

    @PostConstruct
    public void init() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::consume);
    }

    public ConsumerRecords<K, V> consume() {
        System.out.println("********************************配置的第三个消费者***************************");

        makeConsumerConfig();

        try {
            KafkaConsumer<K, V> consumer = new KafkaConsumer<K, V>(consumerProps);
            logger.info("已经订阅的topic: {}", JSON.toJSONString(consumer.subscription()));

//            List<String> topic = new ArrayList<>();
//            topic.add(topic);
//            consumer.subscribe(topic);
        } catch (Exception e) {
            logger.error("ERROR", e);
        }

        return null;
    }
}
