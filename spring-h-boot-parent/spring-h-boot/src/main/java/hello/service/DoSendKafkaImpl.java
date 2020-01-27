package hello.service;

import com.alibaba.fastjson.JSON;
import hello.bean.KafkaProducerBeanConfig;
import hello.service.model.Change;
import hello.service.model.PushData;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;

@Component
@Service(group = "${dubbo.provider.group}",
        methods = {
                @Method(name = "remoteToDubboSync", async = false),
                @Method(name = "remoteToDubboAsync", async = true),
                @Method(name = "remoteToKafka", async = true),}
)
public class DoSendKafkaImpl<K, V> implements DoSendKafka<K, V> {
    private String topicName_1 = "kfk-to-topic-zj-01";
    private String topicName_2 = "kfk-to-topic-zj-02";
    private String topicName_3 = "kfk-to-topic-zj-03";
    private String topicName_4 = "kfk-to-topic-zj-04";

    private static Properties producerProps = new Properties();
    private KafkaProducer<K, V> producer;

    @Autowired
    private KafkaProducerBeanConfig producerBeanConfig;

    private void makeProducerConfig() {
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBeanConfig.getBootstrapServers());
        producerProps.put(ProducerConfig.RETRIES_CONFIG, producerBeanConfig.getRetries());
        producerProps.put(ProducerConfig.BATCH_SIZE_CONFIG, producerBeanConfig.getBatchSize());
        producerProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producerBeanConfig.getBufferMemory());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerBeanConfig.getKeySerializer());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerBeanConfig.getValueSerializer());
    }

    @PostConstruct
    public void init() {
        makeProducerConfig();

        producer = new KafkaProducer<>(producerProps);
    }

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Override
    public String remoteToKafka(String topic) {
        try {
            topic = getTopic(topic);
            String pushData = makeData();

            System.out.println("KAFKA -> " + (null != kafkaTemplate));
            if (null != kafkaTemplate) {
                System.out.println(">>>");
                List<PartitionInfo> partitionInfos = kafkaTemplate.partitionsFor(topic);
                System.out.println("topic[" + topic + "]的partition: " + partitionInfos.size() + "\t" + partitionInfos);

                kafkaTemplate.send(topic, Change.strToByteArray(pushData));
                System.out.println(">>>>>>>>>" + pushData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("发送kafka消息完毕 remoteToKafka");
        return "> KAFKA " + topic;
    }

    @Override
    public String kafkaCustomProducer(String topic) {
        try {
            topic = getTopic(topic);
            String pushData = makeData();

            // 自定义kafka生产者
            ProducerRecord<K, V> producerRecord = new ProducerRecord<K, V>(topic, (V) Change.strToByteArray(pushData));
            Future future = producer.send(producerRecord);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("发送kafka消息完毕 kafkaCustomProducer");
        return "> KAFKA " + topic;
    }

    private String getTopic(String topic) {
        if (StringUtils.isEmpty(topic) || topic.contains("1")) {
            topic = topicName_1;
        } else if (topic.contains("2")) {
            topic = topicName_2;
        } else if (topic.contains("3")) {
            topic = topicName_3;
        } else if (topic.contains("4")) { // 拥有三个 partition
            topic = topicName_4;
        } else {
            topic = "defaultTopic-zj";
        }

        return topic;
    }

    private String makeData() {
        PushData pushData = new PushData();
        pushData.setId("my god");
        pushData.setMsg(UUID.randomUUID().toString());
        pushData.setCode("CHO-20190010");
        pushData.setFlag(2);
        pushData.setTime(new Date());

        return JSON.toJSONString(pushData);
    }
}
