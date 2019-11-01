package hello.service;

public interface DoSendKafka<K, V> {
    public String remoteToKafka(String name);

    String kafkaCustomProducer(String name);
}
