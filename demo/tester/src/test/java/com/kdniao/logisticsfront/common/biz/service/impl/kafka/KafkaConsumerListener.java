package com.kdniao.logisticsfront.common.biz.service.impl.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener implements MessageListener<String, String> {
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        String topic = record.topic();
        String key = record.key();
        String val = record.value();
        long offset = record.offset();
        int partition = record.partition();
        System.out.println(record);
        System.out.printf("receive msg -- \n topic:%s\n key:%s\n val:%s\n offset:%s\n partition:%s \r\n\n",
                topic, key, val, offset, partition);
    }
}
