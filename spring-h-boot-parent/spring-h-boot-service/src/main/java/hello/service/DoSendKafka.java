package hello.service;

import hello.service.model.KafkaRequest;

public interface DoSendKafka<K, V> {
    public String remoteToKafka(KafkaRequest request);

    String kafkaCustomProducer(String name);
}
