package hello.service;

import hello.service.DoHSomething;
import hello.service.model.Change;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Service(group = "${dubbo.provider.group}",
        methods = {
        @Method(name = "remoteToDubboSync", async = false),
        @Method(name = "remoteToDubboAsync", async = true),
        @Method(name = "remoteToKafka", async=true),}
)
public class DoHSomethingImpl implements DoHSomething {
    private String topicName = "kfk-to-topic-zj";
    private String topicName_5 = "kfk-to-topic-zj-05";
    
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    
    @Override
    public String remoteToDubboSync(String name) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        RpcContext rpcContext = RpcContext.getContext();
        System.out.println("remoteToDubboSync RpcContext aa > " + rpcContext.get("aa"));
        System.out.println("remoteToDubboSync RpcContext bb > " + rpcContext.get("bb"));
        System.out.println("RpcContext > " + rpcContext.get(Constants.NAME));
        System.out.println("RpcContext > " + rpcContext.getUrl());
        System.out.println("remoteToDubboSync RpcContext aa > " + rpcContext.getAttachment("aa"));
        System.out.println("remoteToDubboSync RpcContext bb > " + rpcContext.getAttachment("bb"));

        return "remoteToDubboSync " + name;
    }
    
    @Override
    public String remoteToDubboAsync(String name) {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        RpcContext rpcContext = RpcContext.getContext();
        System.out.println("remoteToDubboAsync RpcContext cc > " + rpcContext.get("cc"));
        System.out.println("remoteToDubboAsync RpcContext dd > " + rpcContext.get("dd"));
        System.out.println("RpcContext > " + rpcContext.get(Constants.NAME));
        System.out.println("RpcContext > " + rpcContext.getUrl());
        System.out.println("remoteToDubboAsync RpcContext cc > " + rpcContext.getAttachment("cc"));
        System.out.println("remoteToDubboAsync RpcContext dd > " + rpcContext.getAttachment("dd"));

        return "remoteToDubboAsync " + name;
    }
    
    @Override
    public String remoteToKafka(String name) {
        String result = sendMethod(name);
        System.out.println("发送kafka消息完毕 " + result);
        return result;
    }
    
    private String sendMethod(String name) {
        try {
            System.out.println("KAFKA -> " + (null != kafkaTemplate));
            if (null != kafkaTemplate) {
                System.out.println(">>>");
                List<PartitionInfo> partitionInfos = kafkaTemplate.partitionsFor(topicName_5);
                System.out.println("topic[" + topicName_5 + "]的partition: " + partitionInfos.size() + "\t" + partitionInfos);
                kafkaTemplate.send(topicName_5, Change.strToByteArray(new Date() + " - " + UUID.randomUUID()));
                System.out.println(">>>>>>>>>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "> KAFKA " + name;
    }
    
    @Override
    public String remoteToRabbitmq(String name) {
        System.out.println("RABBITMQ -> " + (null != amqpTemplate));
        if (null != amqpTemplate) {
            System.out.println(">>>>");
            amqpTemplate.send(new Message(name.getBytes(), new MessageProperties()));
            System.out.println(">>>>>>>>>");
        }
        
        String result = "> RABBITMQ " + name;
        System.out.println("发送rabbitmq消息完毕 " + result);
        return result;
    }
    
    @Override
    public String remoteToActivemq(String name) {
        System.out.println("ACTIVEMQ -> " + (null != jmsMessagingTemplate));
        if (null != jmsMessagingTemplate) {
            System.out.println(">>>>");
            Destination destination = new ActiveMQQueue("test.spring.active.queue");
            
            jmsMessagingTemplate.convertAndSend(destination, name);
            System.out.println(">>>>>>>>>");
        }
        
        String result = "> ACTIVEMQ " + name;
        System.out.println("发送activemq消息完毕 " + result);
        return result;
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
