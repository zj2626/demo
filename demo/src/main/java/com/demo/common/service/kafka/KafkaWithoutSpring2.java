package com.demo.common.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class KafkaWithoutSpring2 {
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
                producer.send(new ProducerRecord<>("gs-upload-fuel-order-topic", msg));
                //                producer.send(new ProducerRecord<>("send-auth-order-message-topic", msg));
                log.info("Sent:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            producer.close();
        }

    }

    /*
    独立消费
     */
    @Test
    public void consummer() throws InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");

        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "60000");

        properties.put("fetch.max.wait.ms", "5000");
        properties.put("session.timeout.ms", "30000");
        properties.put("max.poll.records", "10");

        properties.put("auto.offset.reset", "latest");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 不指定分组 (指定了同一个分组也没用)
        // properties.put("group.id", "group-13");

        new Thread(() -> doCunsumer(properties)) {}.start();
        new Thread(() -> doCunsumer(properties)) {}.start();
        Thread.sleep(5000);
        //        log.info("加入第三个消费者 ~~~");
        //        new Thread(() -> doCunsumer(properties)) {}.start();
        Thread.sleep(1000000);
    }

    private void doCunsumer(Properties properties) {
        final KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        // topic 有几个分区
        //        List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor("gs-upload-fuel-order-topic");
        //        partitionInfos.forEach(partitionInfo -> {
        //            log.info(partitionInfo.topic() + " => " + partitionInfo.partition());
        //        });

        kafkaConsumer.assign(
                Arrays.asList(
                        // new TopicPartition("send-auth-order-message-topic", 1),
                        new TopicPartition("gs-upload-fuel-order-topic", 0),
                        new TopicPartition("gs-upload-fuel-order-topic", 1)
                ));

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

                    kafkaConsumer.commitAsync();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            kafkaConsumer.commitSync();
        }
    }

}
