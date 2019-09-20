package com.demo.common.service.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;

/**
 * Title:Server 这是发送消息的服务端
 * Description: 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
 *
 * @author
 */
public class ServerMQTT implements InitializingBean {
    //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    private static final String HOST = "tcp://127.0.0.1:1883";
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String CLIENT_ID = "server-001";
    
    private static MqttClient client;
    
    private String userName = "paho";  //非必须
    private String passWord = "";  //非必须
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // 构造client 默认为以内存保存
        client = new MqttClient(HOST, CLIENT_ID, new MemoryPersistence());
    }
    
    /**
     * 连接服务器
     */
    public MqttTopic connect(String topic) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        // options.setUserName(userName);
        // options.setPassword(passWord.toCharArray());
        // 超时时间
        options.setConnectionTimeout(10);
        // 会话心跳时间
        options.setKeepAliveInterval(20);
        
        try {
            client.setCallback(new PushCallback());
            client.connect(options);
            return client.getTopic(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void publish(MqttTopic topic, MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
    }
    
    @Test
    public void demo() throws Exception {
        ServerMQTT server = new ServerMQTT();
        server.afterPropertiesSet();
        
        // topic
        String topic = "demo_topic_zj_2626";
        
        MqttTopic mqttTopic = server.connect(topic);
        if (null != mqttTopic) {
            MqttMessage message = new MqttMessage();
            message.setQos(1);  //保证消息能到达一次
            message.setRetained(true);
            message.setPayload("这是推送消息的内容".getBytes());
            server.publish(mqttTopic, message);
            System.out.println(message.isRetained() + "------ratained状态");
        }
    }
}
