package com.demo.common.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.util.*;

@Slf4j
public class KafkaWithoutSpring {
    @Test
    public void startProducter() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        //        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<String, String>(properties);
            for (int i = 0; i < 3; i++) {
                String msg = "" + i;
                producer.send(new ProducerRecord<>("demo-upload-fuel-order-topic", msg));
                //                producer.send(new ProducerRecord<>("demo-auth-order-message-topic", msg));
                log.info("Sent:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            producer.close();
        }

    }

    /*
    组消费

    https://www.cnblogs.com/sodawoods-blogs/p/8969774.html
     */
    @Test
    public void consummer() throws InterruptedException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");

        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "60000");

        properties.put("fetch.max.wait.ms", "5000");
        properties.put("session.timeout.ms", "10000");
        properties.put("max.poll.interval.ms", "300000");
        properties.put("max.poll.records", "10");

        properties.put("auto.offset.reset", "latest");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        properties.put("group.id", "group-1");

        new Thread(() -> doCunsumer(properties)) {}.start();
        new Thread(() -> doCunsumer(properties)) {}.start();
        Thread.sleep(5000);
        //        log.info("加入第三个消费者 ~~~"); // TODO 为啥一加入就不停的再均衡
        //        new Thread(() -> doCunsumer(properties)) {}.start();
        Thread.sleep(1000000);
    }

    private void doCunsumer(Properties properties) {
        final KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(
                Arrays.asList(
                        // "demo-auth-order-message-topic",
                        "demo-upload-fuel-order-topic"
                ), new ConsumerRebalanceListener() {
                    @Override
                    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                        log.info("分区再均衡之前");
                        for (TopicPartition p : partitions) {
                            log.info("分配之前消费的分区: " + p.topic() + " => " + p.partition());
                        }
                        kafkaConsumer.commitSync(); // TODO 添加该语句的效果如何
                    }

                    @Override
                    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                        log.info("分区再均衡之后");
                        for (TopicPartition p : partitions) {
                            log.info("分配之后消费的分区: " + p.topic() + " => " + p.partition());
                        }
                    }
                });
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(1500);
                if (records.count() > 0) {
                    log.info("接收到消息个数: " + records.count());
                }
                for (ConsumerRecord<String, String> record : records) {
                    log.info("接收到消息: topic = {}, partition = {}, offset = {}, value = {}", record.topic(), record.partition(), record.offset(), record.value());

                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 异步提交 (采用)
                    kafkaConsumer.commitAsync();

                    // 同步提交
                    // kafkaConsumer.commitSync();

                    // 指定偏移量提交
                    // 1. 如果提交的偏移量小于客户端处理的最后一个消息的偏移量，那么处于两个偏移量之间的消息就会被重复处理(当消费重设的时候)
                    // 2. 如果提交的偏移量大于客户端处理的最后一个消息的偏移量，那么处于两个偏移量之间的消息将会丢失(实际测试中, 会检测到offset异常而重设offset： Fetch offset xx is out of range for partition xxx, resetting offset)
                    // Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
                    // currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() == 81 ? 100 : record.offset() + 1, "no matadata"));
                    // kafkaConsumer.commitAsync(currentOffsets, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            kafkaConsumer.commitSync();
        }
    }
}
