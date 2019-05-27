package com.kdniao.logisticsfront.common.biz.service.impl.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

public class KafkaWithoutSpring {
    @Test
    public void startProducter() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.1.230:9092");
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
            for (int i = 0; i < 100; i++) {
                String msg = i + "";
                producer.send(new ProducerRecord<String, String>("kfk-to-topic-zj-08", msg));
                System.out.println("Sent:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            producer.close();
        }

    }


    @Test
    public void startConsummer() {
        int total = 0;

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.1.230:9092");
        properties.put("group.id", "group-3");
        properties.put("enable.auto.commit", "false");
        properties.put("session.timeout.ms", "15000");
//        properties.put("auto.commit.interval.ms", "60000");
//        properties.put("auto.offset.reset", "earliest");
        properties.put("max.poll.records", "10");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList("kfk-to-topic-zj-08"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            System.out.println(records.count());
            for (ConsumerRecord<String, String> record : records) {
                total++;
                System.out.println(total + " === " + record);
//                System.out.printf("offset = %d, value = %s", record.offset(), record.value());

//                String value = record.value();
//                if(Integer.valueOf(value) == 20){
//                    System.out.println("提交");
//                    kafkaConsumer.commitSync();
//                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
