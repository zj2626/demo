package hello.service.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import hello.service.DoHSomething;
import hello.service.util.Change;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("doHSomethingImpl")
public class DoHSomethingImpl implements DoHSomething {
    private String topicName = "kfk-to-topic-zj";
    private String topicName_5 = "kfk-to-topic-zj-05";
    private String routingKey_5 = "rabbit-to-topic-zj-05";

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private AmqpTemplate amqpTemplate;

    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public DoHSomethingImpl() {
        System.out.println("构造造 DoHSomethingImpl");
    }

    @Override
    public String sayHello(String name) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello****************************** " + name);

        RpcContext rpcContext = RpcContext.getContext();
        System.out.println("RpcContext > " + rpcContext.get("aa"));
        System.out.println("RpcContext > " + rpcContext.get("cc"));
//        System.out.println("RpcContext > " + rpcContext.get(Constants.NAME));
        System.out.println("**********");
        System.out.println("RpcContext > " + rpcContext.getAttachment("aa"));
        System.out.println("RpcContext > " + rpcContext.getAttachment("cc"));
//        System.out.println("RpcContext > " + rpcContext.getAttachment(Constants.REQUESTID_KEY));

        // set requestId
        System.out.println("**************");
//        rpcContext.set(Constants.REQUESTID_KEY, UUID.randomUUID().toString());
//        System.out.println("RpcContext > " + rpcContext.get(Constants.REQUESTID_KEY));
//        System.out.println("RpcContext > " + rpcContext.getAttachment(Constants.REQUESTID_KEY));

        return ">Dubbo " + name;
    }

    @Override
    public String sayShit(String name) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("SHIT****************************** " + name);

        RpcContext rpcContext = RpcContext.getContext();
        System.out.println("RpcContext > " + rpcContext.get("aa"));
        System.out.println("RpcContext > " + rpcContext.get("cc"));
//        System.out.println("RpcContext > " + rpcContext.get(Constants.REQUESTID_KEY));
        System.out.println("**********");
        System.out.println("RpcContext > " + rpcContext.getAttachment("aa"));
        System.out.println("RpcContext > " + rpcContext.getAttachment("cc"));
//        System.out.println("RpcContext > " + rpcContext.getAttachment(Constants.REQUESTID_KEY));

        return "shit u " + name;
    }

    @Override
    public String sayFuckToKafka(String name) {
        String result = sendMethod(name);
        System.out.println("发送kafka消息完毕 " + result);
        return result;
    }

    private String sendMethod(String name) {
        System.out.println("KAFKA -> " + (null != kafkaTemplate));
        if (null != kafkaTemplate) {
            System.out.println(">>>");
            List<PartitionInfo> partitionInfos = kafkaTemplate.partitionsFor(topicName_5);
            System.out.println("topic[" + topicName_5 + "]的partition: " + partitionInfos.size() + "\t" + partitionInfos);
            kafkaTemplate.send(topicName_5, Change.strToByteArray(new Date() + " - " + UUID.randomUUID()));
            System.out.println(">>>>>>>>>");
        }

        return "> KAFKA" + name;
    }

    @Override
    public String sayFuckToRabbitmq(String name) {
        String result = sendMethodRabbit(name);
        System.out.println("发送rabbitmq消息完毕 " + result);
        return result;
    }

    private String sendMethodRabbit(String name) {
        System.out.println("RABBITMQ -> " + (null != amqpTemplate));
        if (null != amqpTemplate) {
            System.out.println(">>>>");
            amqpTemplate.send(new Message(name.getBytes(), new MessageProperties()));
            System.out.println(">>>>>>>>>");
        }

        return "> RABBITMQ " + name;
    }

//    /**
//     * 发送消息
//     */
//    @Test
//    public void sendMethodByProducer() {
//        byte[] msg = Change.strToByteArray(new Date() + " - " + UUID.randomUUID());
//
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.1.230:9092");
//        props.put("acks", "all");
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
//
//        KafkaProducer producer = new KafkaProducer(props);
//
//        producer.send(new ProducerRecord(topicName, msg), new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                System.out.println(metadata);
//                System.out.println(exception);
//            }
//        });
////        Future future = producer.send(new ProducerRecord(topicName, msg));
//
//        producer.flush();
//        producer.close();
//    }
//
//    /**
//     * Kafka client 消息接收的三种模式
//     * 1.最多一次：客户端收到消息后，在处理消息前自动提交，这样kafka就认为consumer已经消费过了，偏移量增加。
//     * 2.最少一次：客户端收到消息，处理消息，再提交反馈。这样就可能出现消息处理完了，在提交反馈前，网络中断或者程序挂了，那么kafka认为这个消息还没有被consumer消费，产生重复消息推送。
//     * 3.正好一次：保证消息处理和提交反馈在同一个事务中，即有原子性。
//     */
//    @Test
//    public void getMethodByConsumer1() {
//        /*最多一次*/
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.1.230:9092");
//        props.put("group.id", "g1");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
//        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
//        consumer.subscribe(Collections.singletonList(topicName));
//
//        while (true) {
//            ConsumerRecords<String, byte[]> records = consumer.poll(100);
//            for (ConsumerRecord<String, byte[]> record : records) {
//                System.out.println(record.partition() + "_" + record.offset() + "_" + Change.byteArrayToStr(record.value()));
//            }
//        }
//    }
//
//    @Test
//    public void getMethodByConsumer2() {
//        /*最少一次*/
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.1.230:9092");
//        props.put("group.id", "g1");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "99999999");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
//        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
//        consumer.subscribe(Collections.singletonList(topicName));
//
//        while (true) {
//            ConsumerRecords<String, byte[]> records = consumer.poll(100);
//            for (ConsumerRecord<String, byte[]> record : records) {
//                System.out.println(record.partition() + "_" + record.offset() + "_" + Change.byteArrayToStr(record.value()));
//                consumer.commitAsync();
//            }
//        }
//    }
//
//    @Test
//    public void getMethodByConsumer21() {
//        /*最少一次*/
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.1.230:9092");
//        props.put("group.id", "g1");
//        props.put("enable.auto.commit", "false");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
//        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
//        consumer.subscribe(Collections.singletonList(topicName));
//
//        while (true) {
//            ConsumerRecords<String, byte[]> records = consumer.poll(100);
//            for (ConsumerRecord<String, byte[]> record : records) {
//                System.out.println(record.partition() + "_" + record.offset() + "_" + Change.byteArrayToStr(record.value()));
//                consumer.commitAsync();
//            }
//        }
//    }
//
//    @Test
//    public void getMethodByConsumer3() {
//        /*正好一次*/
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.1.230:9092");
//        props.put("group.id", "g1");
//        props.put("enable.auto.commit", "false");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
//        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
//        consumer.subscribe(Collections.singletonList(topicName));
//
//        while (true) {
//            ConsumerRecords<String, byte[]> records = consumer.poll(100);
//            for (ConsumerRecord<String, byte[]> record : records) {
//                boolean isException = false;
//                try {
//                    System.out.println(record.partition() + "_" + record.offset() + "_" + Change.byteArrayToStr(record.value()));
//
//                    if (record.offset() % 9 == 0) {
//                        throw new Exception("error info");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    isException = true;
//                }
//                if (isException) {
//                    //处理发生异常，说明数据没有被消费，为了保证能被消费，需要移动到该位置继续进行处理
//                    TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
//                    consumer.seek(topicPartition, record.offset());
//                    break;
//                }
//            }
//        }
//    }

}
