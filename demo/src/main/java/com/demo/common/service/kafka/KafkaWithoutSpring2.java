package com.demo.common.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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
            for (int i = 0; i < 2; i++) {
                String msg = "" + i;
                producer.send(new ProducerRecord<>("gs-upload-fuel-order-topic", msg));
                //                producer.send(new ProducerRecord<>("send-auth-order-message-topic", msg));
                //                producer.send(new ProducerRecord<>("dingding-talk-text-notify-topic", msg));
                //                producer.send(new ProducerRecord<>("reload-permission-user-topic", msg));
                System.out.println("Sent:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            producer.close();
        }

    }

    @Test
    public void startConsummer1() {
        consummer();
    }

    @Test
    public void startConsummer2() {
        consummer();
    }

    @Test
    public void startConsummer3() {
        consummer();
    }

    /*
    独立消费
     */
    @Test
    public void consummer() {
        int total = 0;

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

        // 不指定分组
        // properties.put("group.id", "group-13");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor("gs-upload-fuel-order-topic");
        partitionInfos.forEach(partitionInfo -> {
            System.out.println(partitionInfo.topic() + " => " + partitionInfo.partition());
        });

        kafkaConsumer.assign(
                Arrays.asList(
                        new TopicPartition("gs-upload-fuel-order-topic", 0),
                        new TopicPartition("gs-upload-fuel-order-topic", 1),
                        new TopicPartition("send-auth-order-message-topic", 1)
                ));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            // System.out.println("接收到消息个数: " + records.count());
            for (ConsumerRecord<String, String> record : records) {
                try {
                    total++;
                    System.out.print("    " + total + " === ");
                    System.out.printf("topic = %s, partition = %d, offset = %d, value = %s", record.topic(), record.partition(), record.offset(), record.value());
                    System.out.println();

                    String value = record.value();
                    if (Integer.valueOf(value) == 20) {
                        //                    System.out.println("提交");
                        //                    kafkaConsumer.commitAsync();
                    }
                    kafkaConsumer.commitAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                    kafkaConsumer.commitSync();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                kafkaConsumer.commitSync();
            }
        }
    }


}
